package cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String field = request.getParameter("field");
        String newValue = request.getParameter("newValue");
        Integer userId = (Integer) request.getSession(false).getAttribute("userid");

        if (userId == null || field == null || newValue == null || newValue.trim().isEmpty()) {
            response.getWriter().write("无效的输入");
            return;
        }

        // 更新数据库
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "UPDATE users SET " + field + " = ? WHERE id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, newValue);
                stmt.setInt(2, userId);
                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    // 更新 session 中的值
                    request.getSession(false).setAttribute("user" + field, newValue);
                    response.getWriter().write("修改成功");
                } else {
                    response.getWriter().write("修改失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("发生错误: " + e.getMessage());
        }
    }

    private String getSessionValue(String field, HttpServletRequest request) {
        return (String) request.getSession(false).getAttribute("user" + field);
    }
}
