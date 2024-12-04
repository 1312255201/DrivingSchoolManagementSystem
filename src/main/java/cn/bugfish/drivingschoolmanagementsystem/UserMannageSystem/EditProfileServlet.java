package cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import cn.bugfish.drivingschoolmanagementsystem.DataBase.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/EditProfileServlet")
public class EditProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String field = request.getParameter("field");

        // 确保用户已经登录
        if (request.getSession(false) == null || request.getSession(false).getAttribute("userid") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 转发到编辑页面
        request.setAttribute("field", field);
        request.setAttribute("value", getSessionValue(field, request));
        request.getRequestDispatcher("editField.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String field = request.getParameter("field");
        String newValue = request.getParameter("newValue");
        HttpSession session = request.getSession(false);

        if (session == null || field == null || newValue == null) {
            response.getWriter().write("failure");
            return;
        }
        UserDao userDao = new UserDao();
        boolean success = false;
        User user = userDao.getUserById((Integer) request.getSession(false).getAttribute("userid"));
        // 更新 session 或数据库
        if ("name".equals(field)) {
            session.setAttribute("username", newValue);
            success = userDao.updateUser(user,"name",newValue);
        } else if ("phonenumber".equals(field)) {
            session.setAttribute("userphonenumber", newValue);
            success = userDao.updateUser(user,"phonenumber",newValue);
        } else if ("email".equals(field)) {
            session.setAttribute("useremail", newValue);
            success = userDao.updateUser(user,"email",newValue);
        } else if ("idnumber".equals(field)) {
            session.setAttribute("useridnumber", newValue);
            success = userDao.updateUser(user,"idnumber",newValue);
        }
        if (success)
        {
            response.getWriter().write("success");
        }
        else{
            response.getWriter().write("fail");
        }
    }

    private String getSessionValue(String field, HttpServletRequest request) {
        return (String) request.getSession(false).getAttribute("user" + field);
    }
}
