package login;

import com.google.gson.Gson;
import model.HttpResult;
import model.UserModel;
import utils.DBUtils;
import utils.LogUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/sys")
public class SysServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpResult result = new HttpResult();
        // 设置响应内容类型
        response.setContentType("text/json; charset=utf-8");
        //  乱码原因 : 编码格式要设置后才能把writer取出 否则取出的是没有设置编码格式的out
        PrintWriter out = response.getWriter();
        //  取到请求名
        String urlType = request.getParameter("urlType");
        //  判断请求
        switch (urlType) {
            //  请求所有数据
            case "selectAllUserList":
                try {
                    List<UserModel> modelList = DBUtils.selectAllUserList();
                    result.setCode("0");
                    result.setMsg("查询成功");
                    result.setData(modelList);
                    LogUtils.logI("SysServlet", "doPost" + new Gson().toJson(modelList));
                    out.print(new Gson().toJson(result));
                    break;
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            case "delUser":
                //  取出数据
                String uID = request.getParameter("uID");
                //  执行删除
                result.setCode("" + DBUtils.delUser(uID));
                result.setMsg("删除成功");
                result.setData("ppp");
                out.print(new Gson().toJson(result));
                break;
            case "addUser":
                //  取出数据
                String uName = URLDecoder.decode(request.getParameter("uName"),"utf-8");
                String uAddress = URLDecoder.decode(request.getParameter("uAddress"),"utf-8");
                String uMobile = request.getParameter("uMobile");
                String uSex = URLDecoder.decode(request.getParameter("uSex"),"utf-8");
                String uType = request.getParameter("uType");
                String uPass = request.getParameter("uPass");

                //  组合数据
                UserModel userModel = new UserModel(uName, uPass, uSex, uType, uAddress, uMobile);
                LogUtils.logI("SysServlet", "doPost 添加用户 = " + new Gson().toJson(userModel));
                //  执行插入数据
                int insertCode = DBUtils.addUser(userModel);
                if (insertCode == 0) {
                    //  新增成功
                    result.setCode("0");
                    result.setMsg("插入成功");
                    result.setData("插入成功");
                    out.print(new Gson().toJson(result));
                } else {
                    //  新增失败
                    result.setCode("-1");
                    result.setMsg("插入失败");
                    result.setData("插入失败");
                    out.print(new Gson().toJson(result));
                }
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
