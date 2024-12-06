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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/available-schedule")
public class AvailableScheduleServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String level = req.getParameter("level");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT st.id, st.start_time, st.end_time, st.max_students - COUNT(sa.id) AS remaining_slots, u.name AS coach_name " +
                    "FROM schedule_time st " +
                    "LEFT JOIN student_appointment sa ON st.id = sa.schedule_id " +
                    "JOIN users u ON st.coach_id = u.id " +
                    "WHERE st.level = ? " +
                    "GROUP BY st.id";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, level);
            ResultSet rs = stmt.executeQuery();

            List<Map<String, Object>> schedules = new ArrayList<>();
            while (rs.next()) {
                Map<String, Object> schedule = new HashMap<>();
                schedule.put("id", rs.getInt("id"));
                schedule.put("start_time", rs.getString("start_time"));
                schedule.put("end_time", rs.getString("end_time"));
                schedule.put("remaining_slots", rs.getInt("remaining_slots"));
                schedule.put("coach_name", rs.getString("coach_name"));
                schedules.add(schedule);
            }

            resp.setContentType("application/json");
            resp.getWriter().write(new Gson().toJson(schedules));
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
