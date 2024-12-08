package cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem.Controler;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

@WebServlet("/reset-password")
public class ForgetResetPasswordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("password");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (token == null || token.isEmpty() || newPassword == null || newPassword.isEmpty()) {
            response.getWriter().write("{\"success\": false, \"message\": \"请求参数无效！\"}");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            // 检查令牌的有效性
            String queryTokenSql = """
                SELECT user_id, created_at FROM password_resets WHERE token = ? LIMIT 1
                """;
            try (PreparedStatement psToken = conn.prepareStatement(queryTokenSql)) {
                psToken.setString(1, token);
                ResultSet rs = psToken.executeQuery();

                if (!rs.next()) {
                    response.getWriter().write("{\"success\": false, \"message\": \"无效的令牌！\"}");
                    return;
                }

                int userId = rs.getInt("user_id");
                Timestamp createdAt = rs.getTimestamp("created_at");

                // 检查令牌是否过期（1小时内有效）
                long currentTime = System.currentTimeMillis();
                if (currentTime - createdAt.getTime() > 3600000) {
                    response.getWriter().write("{\"success\": false, \"message\": \"令牌已过期！\"}");
                    return;
                }

                // 更新用户密码
                String updatePasswordSql = """
                    UPDATE users SET password = ? WHERE id = ?
                    """;
                try (PreparedStatement psUpdate = conn.prepareStatement(updatePasswordSql)) {
                    String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                    psUpdate.setString(1, hashedPassword);
                    psUpdate.setInt(2, userId);
                    int rowsUpdated = psUpdate.executeUpdate();

                    if (rowsUpdated > 0) {
                        // 删除已使用的令牌
                        String deleteTokenSql = "DELETE FROM password_resets WHERE token = ?";
                        try (PreparedStatement psDelete = conn.prepareStatement(deleteTokenSql)) {
                            psDelete.setString(1, token);
                            psDelete.executeUpdate();
                        }

                        response.getWriter().write("{\"success\": true, \"message\": \"密码已成功重置！\"}");
                    } else {
                        response.getWriter().write("{\"success\": false, \"message\": \"密码重置失败，请稍后再试！\"}");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"服务器错误，请稍后再试！\"}");
        }
    }


}
