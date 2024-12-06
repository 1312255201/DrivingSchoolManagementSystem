package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/book-schedule")
public class BookScheduleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int studentId = (int) request.getSession().getAttribute("userid"); // 从会话中获取学员ID
        int scheduleId = Integer.parseInt(request.getParameter("schedule_id")); // 获取时间段ID

        try (Connection conn = DBUtil.getConnection()) {
            String checkSql2 = """
                select * from appointment where student_id = ? and schedule_id = ?
                """;
            try (PreparedStatement psCheck = conn.prepareStatement(checkSql2)) {
                psCheck.setInt(1, studentId);
                psCheck.setInt(2, scheduleId);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        response.setContentType("application/json");
                        response.getWriter().write("{\"success\": false, \"message\": \"你已经预约过这场了！\"}");
                        return;
                    }
                }
            }
            // 检查当前时间段是否还有剩余名额
            String checkSql = """
                SELECT (s.capacity - COUNT(a.id)) AS remaining
                FROM schedule s
                LEFT JOIN appointment a ON s.id = a.schedule_id
                WHERE s.id = ?
                GROUP BY s.id
                HAVING remaining > 0
                """;
            try (PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
                psCheck.setInt(1, scheduleId);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (!rs.next()) {
                        response.setContentType("application/json");
                        response.getWriter().write("{\"success\": false, \"message\": \"该时间段已满！\"}");
                        return;
                    }
                }
            }

            // 插入预约记录
            String insertSql = "INSERT INTO appointment (student_id, schedule_id) VALUES (?, ?)";
            try (PreparedStatement psInsert = conn.prepareStatement(insertSql)) {
                psInsert.setInt(1, studentId);
                psInsert.setInt(2, scheduleId);
                psInsert.executeUpdate();
            }

            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true, \"message\": \"预约成功！\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"预约失败，请稍后重试！\"}");
        }
    }
}
