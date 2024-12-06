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

@WebServlet("/student-appointments")
public class StudentAppointmentsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int studentId = (int) request.getSession().getAttribute("userid");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                SELECT a.id, s.date, s.time 
                FROM appointment a
                JOIN schedule s ON a.schedule_id = s.id
                WHERE a.student_id = ?
                """;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, studentId);
                try (ResultSet rs = ps.executeQuery()) {
                    List<Map<String, String>> appointments = new ArrayList<>();
                    while (rs.next()) {
                        Map<String, String> appt = new HashMap<>();
                        appt.put("id", String.valueOf(rs.getInt("id")));
                        appt.put("date", rs.getString("date"));
                        appt.put("time", rs.getString("time"));
                        appointments.add(appt);
                    }
                    response.setContentType("application/json");
                    response.getWriter().write(new Gson().toJson(appointments));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
