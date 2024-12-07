package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/view-leave-requests")
public class ViewLeaveRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int coachId = (int) request.getSession().getAttribute("userid"); // 获取当前教练的ID
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM leave_requests WHERE coach_id = ? ORDER BY created_at DESC";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, coachId);
                try (ResultSet rs = ps.executeQuery()) {
                    List<Map<String, Object>> leaveRequests = new ArrayList<>();
                    while (rs.next()) {
                        Map<String, Object> leaveRequest = new HashMap<>();
                        leaveRequest.put("id", rs.getInt("id"));
                        leaveRequest.put("reason", rs.getString("reason"));
                        leaveRequest.put("start_date", rs.getDate("start_date").toString());
                        leaveRequest.put("end_date", rs.getDate("end_date").toString());
                        leaveRequest.put("status", rs.getString("status"));
                        leaveRequest.put("created_at", rs.getTimestamp("created_at").toString());
                        leaveRequests.add(leaveRequest);
                    }
                    PrintWriter out = response.getWriter();
                    out.write(new com.google.gson.Gson().toJson(leaveRequests));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"获取请假记录失败！\"}");
        }
    }
}
