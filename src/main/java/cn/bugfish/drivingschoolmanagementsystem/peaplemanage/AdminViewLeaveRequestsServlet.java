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

@WebServlet("/admin-view-leave-requests")
public class AdminViewLeaveRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                SELECT lr.id, lr.reason, lr.start_date, lr.end_date, lr.status, lr.created_at,
                       u.name AS coach_name
                FROM leave_requests lr
                JOIN users u ON lr.coach_id = u.id
                ORDER BY lr.created_at DESC
                """;
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                List<Map<String, Object>> leaveRequests = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> requestDetails = new HashMap<>();
                    requestDetails.put("id", rs.getInt("id"));
                    requestDetails.put("coach_name", rs.getString("coach_name"));
                    requestDetails.put("reason", rs.getString("reason"));
                    requestDetails.put("start_date", rs.getDate("start_date").toString());
                    requestDetails.put("end_date", rs.getDate("end_date").toString());
                    requestDetails.put("status", rs.getString("status"));
                    requestDetails.put("created_at", rs.getTimestamp("created_at").toString());
                    leaveRequests.add(requestDetails);
                }
                PrintWriter out = response.getWriter();
                out.write(new com.google.gson.Gson().toJson(leaveRequests));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"获取请假记录失败！\"}");
        }
    }
}
