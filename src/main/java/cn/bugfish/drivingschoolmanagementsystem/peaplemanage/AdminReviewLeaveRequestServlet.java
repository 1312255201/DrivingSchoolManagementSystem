package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/admin-review-leave-request")
public class AdminReviewLeaveRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int adminId = (int) request.getSession().getAttribute("userid"); // 管理员ID
        int requestId = Integer.parseInt(request.getParameter("id"));
        String action = request.getParameter("action");
        String comments = request.getParameter("comments");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                UPDATE leave_requests
                SET status = ?, review_comments = ?, admin_id = ?, reviewed_at = CURRENT_TIMESTAMP
                WHERE id = ?
                """;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, action.equals("approved") ? "已批准" : "已拒绝");
                ps.setString(2, comments);
                ps.setInt(3, adminId);
                ps.setInt(4, requestId);
                int rowsUpdated = ps.executeUpdate();
                response.setContentType("application/json");
                response.getWriter().write(rowsUpdated > 0
                        ? "{\"success\": true, \"message\": \"审核完成！\"}"
                        : "{\"success\": false, \"message\": \"审核失败，请稍后再试！\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"服务器错误，请稍后再试！\"}");
        }
    }
}
