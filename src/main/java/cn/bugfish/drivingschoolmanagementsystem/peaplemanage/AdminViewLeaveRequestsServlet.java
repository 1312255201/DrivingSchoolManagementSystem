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
/**
 * 管理员查看请假请求的 Servlet
 * 该 Servlet 接收 GET 请求，从数据库中获取所有请假请求，并将其以 JSON 格式返回给客户端
 */
@WebServlet("/admin-view-leave-requests")
public class AdminViewLeaveRequestsServlet extends HttpServlet {
    /**
     * 管理员查看请假请求的 Servlet
     * 该 Servlet 接收 GET 请求，从数据库中获取所有请假请求，并将其以 JSON 格式返回给客户端
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 设置响应的内容类型为 JSON，并指定字符编码为 UTF-8
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (Connection conn = DBUtil.getConnection()) {
            // 定义 SQL 查询语句，用于从 leave_requests 表和 users 表中获取请假请求的详细信息
            String sql = """
                SELECT lr.id, lr.reason, lr.start_date, lr.end_date, lr.status, lr.created_at,
                       u.name AS coach_name
                FROM leave_requests lr
                JOIN users u ON lr.coach_id = u.id
                ORDER BY lr.created_at DESC
                """;
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                // 创建一个 List 来存储请假请求的详细信息
                List<Map<String, Object>> leaveRequests = new ArrayList<>();
                // 遍历查询结果集，将每个请假请求的详细信息存储到 Map 中，并添加到 List 中
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
                // 获取响应的输出流，并将请假请求的详细信息以 JSON 格式写入到响应中
                PrintWriter out = response.getWriter();
                out.write(new com.google.gson.Gson().toJson(leaveRequests));
            }
        } catch (Exception e) {
            // 打印异常堆栈跟踪
            e.printStackTrace();
            // 如果发生异常，向客户端返回一个包含错误信息的 JSON 响应
            response.getWriter().write("{\"success\": false, \"message\": \"获取请假记录失败！\"}");
        }
    }
}
