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
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/sys")
public class SysServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpResult result = new HttpResult();
        PrintWriter out = response.getWriter();
        // 设置响应内容类型
        response.setContentType("text/json; charset=utf-8");
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
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
