package cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Controler;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Loger.UserMannageSystemLoger;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从请求参数中获取用户ID
        String id = request.getParameter("id");
        String defaultPassword = "password"; // 默认密码

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // 获取数据库连接
            conn = DBUtil.getConnection();
            // 定义SQL语句，用于更新用户密码
            String sql = "UPDATE users SET password = ? WHERE id = ?";
            // 创建PreparedStatement对象，用于执行SQL语句
            pstmt = conn.prepareStatement(sql);

            // 使用 BCrypt 对默认密码进行加密
            String hashedPassword = BCrypt.hashpw(defaultPassword, BCrypt.gensalt());
            // 设置SQL语句中的参数
            pstmt.setString(1, hashedPassword);
            pstmt.setInt(2, Integer.parseInt(id));
            // 执行SQL语句，更新用户密码
            pstmt.executeUpdate();

            // 重定向回用户管理页面
            response.sendRedirect("dashboard.jsp?section=usermanage");
        } catch (Exception e) {
            UserMannageSystemLoger.logger.error(e.getStackTrace());
            response.sendRedirect("dashboard.jsp?section=usermanage&error=resetFailed");
        } finally {
            try {
                if (pstmt != null) pstmt.close();
            } catch (SQLException ignored) {}
            try {
                if (conn != null) conn.close();
            } catch (SQLException ignored) {}
        }
    }
}
