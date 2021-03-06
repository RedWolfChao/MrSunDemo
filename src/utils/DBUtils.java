package utils;

import model.UserModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * MySQL相关操作类
 */
public class DBUtils {
    //  ?useUnicode=true&characterEncoding=utf8 加入这个是为了解决Java往SQl插入数据时中文乱码问题
    //  URL : https://blog.csdn.net/lsr40/article/details/78736855
    private static final String DB_URL = "jdbc:mysql://localhost:3306/demo?useUnicode=true&characterEncoding=utf8";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "123456";
    private static final String DB_DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static Connection conn;

    //  静态代码块中初始化数据
    static {
        try {
            //  加载驱动程序
            Class.forName(DB_DRIVER_NAME);
            //  获得数据库连接
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getInstance() {
        return conn;
    }

    /**
     * 向某表中插入数据
     *
     * @param conn    Connection
     * @param tName   表名
     * @param dataMap 要插入的数据
     * @return 0 插入成功 -1 失败
     */
    public static int insertData(Connection conn, String tName, HashMap<String, String> dataMap) {
        if (conn == null) {
            throw new NullPointerException("insertData conn is null");
        }
        if (tName == null || "".equals(tName)) {
            throw new NullPointerException("insertData tName is null");
        }
        //  拼接key
        StringBuilder sbKey = new StringBuilder();
        StringBuilder sbValue = new StringBuilder();
        //  取出key
        List<String> keyList = new ArrayList<>(dataMap.keySet());
        //  拼接Key Value
        for (int i = 0; i < keyList.size(); i++) {
            //  最后一个不拼接","
            if (i == keyList.size() - 1) {
                sbKey.append(keyList.get(i));
                sbValue.append("?");
            } else {
                sbKey.append(keyList.get(i) + ",");
                sbValue.append("?,");
            }
        }
        //  最终组合
        String insertSQL = "INSERT INTO " + tName + " (" + sbKey + ")values(" + sbValue + ")";
        //  执行语句
        try {
            PreparedStatement ps = conn.prepareStatement(insertSQL);
            //  塞入值
            for (int i = 0; i < keyList.size(); i++) {
                ps.setString(i + 1, dataMap.get(keyList.get(i)));
            }
            //  执行语句
            ps.executeUpdate();
            //  释放资源
            ps.close();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 删除某表中的某数据
     *
     * @param conn     Connection
     * @param tName    表名
     * @param delKey   删除的字段名
     * @param delValue 删除字段对应的值
     * @return 0 删除成功 -1删除失败
     */
    public static int deleteData(Connection conn, String tName, String delKey, String delValue) {
        if (conn == null) {
            throw new NullPointerException("deleteData conn is null");
        }
        if (tName == null || "".equals(tName)) {
            throw new NullPointerException("deleteData tName is null");
        }

        //  最终组合
        String deleteSql = "DELETE FROM " + tName + " WHERE " + delKey + " = '" + delValue + "'";
        //  执行语句
        try {
            Statement stat = conn.createStatement();
            //
            stat.executeUpdate(deleteSql);
            stat.close();
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 登录
     *
     * @param uName 用户名
     * @param uPass 密码
     * @return UserModel
     * @throws SQLException e
     */
    public static UserModel login(String uName, String uPass) throws SQLException {
        //  登录操作
        //  连接数据库查询当前用户信息
        Connection conn = DBUtils.getInstance();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT uID, uName,uType,uMobile,uSex,uAddress FROM user WHERE uName = '" + uName + "' AND uPass = '" + uPass + "'");
        List<UserModel> userList = new ArrayList<>();
        UserModel userModel;
        //  如果不非法操作 就一个
        while (rs.next()) {
            userModel = new UserModel();
            userModel.setuName(rs.getString("uName"));
            userModel.setuID(rs.getString("uId"));
            userModel.setuMobile(rs.getString("uMobile"));
            userModel.setuType(rs.getString("uType"));
            userModel.setuSex(rs.getString("uSex"));
            userModel.setuAddress(rs.getString("uAddress"));
            userList.add(userModel);
        }
        if (userList.size() > 0) {
            return userList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 查询用户列表
     */
    public static List<UserModel> selectAllUserList() throws SQLException {
        //  登录操作
        //  连接数据库查询当前用户信息
        Connection conn = DBUtils.getInstance();
        Statement stmt = conn.createStatement();
        //  查询所有用户列表的接口只有系统管理员有 所以不能查到自己
        ResultSet rs = stmt.executeQuery("SELECT uID, uName,uType,uMobile,uSex,uAddress FROM user WHERE uType <> '0'");
        List<UserModel> userList = new ArrayList<>();
        UserModel userModel;
        while (rs.next()) {
            userModel = new UserModel();
            userModel.setuName(rs.getString("uName"));
            userModel.setuID(rs.getString("uId"));
            userModel.setuMobile(rs.getString("uMobile"));
            userModel.setuType(rs.getString("uType"));
            userModel.setuSex(rs.getString("uSex"));
            userModel.setuAddress(rs.getString("uAddress"));
            userList.add(userModel);
        }
        return userList;
    }

    /**
     * 根据uID 删除指定用户
     *
     * @param uID
     * @return
     */
    public static int delUser(String uID) {
        return deleteData(DBUtils.getInstance(), "user", "uID", uID);
    }

    /**
     * 添加用户
     * ID
     * 姓名
     * 地址
     * 性别
     * 密码
     */
    public static int addUser(UserModel userModel) {
        HashMap<String, String> inMap = new HashMap<>();
        inMap.put("uName", userModel.getuName());
        inMap.put("uAddress", userModel.getuAddress());
        inMap.put("uSex", userModel.getuSex());
        inMap.put("uMobile", userModel.getuMobile());
        inMap.put("uType", userModel.getuType());
        inMap.put("uPass", userModel.getuPass());
        return insertData(DBUtils.getInstance(), "user", inMap);
    }
}
