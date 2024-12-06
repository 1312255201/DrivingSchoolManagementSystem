package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;
import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@WebServlet("/available-schedules")
public class AvailableSchedulesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                SELECT s.id, s.date, s.time, 
                       (s.capacity - COUNT(a.id)) AS remaining
                FROM schedule s
                LEFT JOIN appointment a ON s.id = a.schedule_id
                GROUP BY s.id
                HAVING remaining > 0
                """;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                List<Map<String, Object>> schedules = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> schedule = new HashMap<>();
                    schedule.put("id", rs.getInt("id"));
                    schedule.put("date", rs.getString("date"));
                    schedule.put("time", rs.getString("time"));
                    schedule.put("remaining", rs.getInt("remaining"));
                    schedules.add(schedule);
                }
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(schedules));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
