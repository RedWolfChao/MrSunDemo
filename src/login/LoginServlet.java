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

@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpResult result = new HttpResult();
        // 设置响应内容类型
        response.setContentType("text/json; charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String uName = request.getParameter("uName");
        String uPass = request.getParameter("uPass");
        PrintWriter out = response.getWriter();
        LogUtils.logI("LoginServlet", "doPost uName = " + uName + " uPass= " + uPass);
        try {
            UserModel userModel = DBUtils.login(uName, uPass);
            if (userModel == null) {
                result.setCode("-1");
                result.setMsg("该用户不存在");
                result.setData("");
            } else {
                result.setCode("0");
                result.setMsg("登录成功");
                result.setData(userModel);
            }
            out.print(new Gson().toJson(result));
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode("-1");
            result.setMsg("程序异常,请稍后再试");
            result.setData("" + e.getMessage());
            out.print(new Gson().toJson(result));
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
        //  修改GIT 配置测试
    }
}
