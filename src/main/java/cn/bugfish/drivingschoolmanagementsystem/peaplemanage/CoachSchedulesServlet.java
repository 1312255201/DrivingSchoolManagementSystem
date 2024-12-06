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

@WebServlet("/coach-schedules")
public class CoachSchedulesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int coachId = (int) request.getSession().getAttribute("userid");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT id, date, time FROM schedule WHERE coach_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, coachId);
                try (ResultSet rs = ps.executeQuery()) {
                    List<Map<String, String>> schedules = new ArrayList<>();
                    while (rs.next()) {
                        Map<String, String> schedule = new HashMap<>();
                        schedule.put("id", String.valueOf(rs.getInt("id")));
                        schedule.put("date", rs.getString("date"));
                        schedule.put("time", rs.getString("time"));
                        schedules.add(schedule);
                    }
                    response.setContentType("application/json");
                    response.getWriter().write(new Gson().toJson(schedules));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
