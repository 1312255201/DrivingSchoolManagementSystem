package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;


import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/submit-leave-request")
public class SubmitLeaveRequestServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int coachId = (int) request.getSession().getAttribute("userid"); // 获取当前教练的ID
        String reason = request.getParameter("reason");
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO leave_requests (coach_id, reason, start_date, end_date) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, coachId);
                ps.setString(2, reason);
                ps.setString(3, startDate);
                ps.setString(4, endDate);
                ps.executeUpdate();
            }
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true, \"message\": \"请假申请已提交！\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"提交失败，请稍后重试！\"}");
        }
    }
}
