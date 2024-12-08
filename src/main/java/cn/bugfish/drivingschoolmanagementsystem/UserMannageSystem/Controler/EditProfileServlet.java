package cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Controler;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.UserDao;
import cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

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
        String confirmValue = request.getParameter("confirmValue");
        HttpSession session = request.getSession(false);

        // 基础校验
        if (session == null || field == null || newValue == null) {
            response.getWriter().write("failure");
            return;
        }

        UserDao userDao = new UserDao();
        boolean success = false;
        Integer userId = (Integer) session.getAttribute("userid");
        User user = userDao.getUserById(userId);

        if (user == null) {
            response.getWriter().write("failure");
            return;
        }

        // 处理不同字段的更新逻辑
        switch (field) {
            case "name":
                session.setAttribute("username", newValue);
                success = userDao.updateUser(user, "name", newValue);
                break;

            case "phonenumber":
                // 验证当前密码
                if (confirmValue == null || !confirmValue.equals(user.getPassword())) {
                    response.getWriter().write("confirm-fail"); // 确认失败
                    return;
                }
                session.setAttribute("userphonenumber", newValue);
                success = userDao.updateUser(user, "phonenumber", newValue);
                break;

            case "email":
                session.setAttribute("useremail", newValue);
                success = userDao.updateUser(user, "email", newValue);
                break;

            case "idnumber":
                session.setAttribute("useridnumber", newValue);
                success = userDao.updateUser(user, "idnumber", newValue);
                break;

            case "password":
                // 验证当前密码
                if (confirmValue == null || !confirmValue.equals(user.getPassword())) {
                    response.getWriter().write("confirm-fail"); // 确认失败
                    return;
                }
                success = userDao.updateUser(user, "password", newValue);
                break;

            default:
                response.getWriter().write("invalid-field"); // 无效字段
                return;
        }

        // 返回结果
        if (success) {
            response.getWriter().write("success");
        } else {
            response.getWriter().write("fail");
        }
    }


    private String getSessionValue(String field, HttpServletRequest request) {
        return (String) request.getSession(false).getAttribute("user" + field);
    }
}
