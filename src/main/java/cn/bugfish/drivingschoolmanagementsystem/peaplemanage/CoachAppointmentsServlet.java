package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import com.google.gson.Gson;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/coach-appointments")
public class CoachAppointmentsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int coachId = (int) request.getSession().getAttribute("userid");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                SELECT a.id AS appointment_id, u.name AS student_name, s.date, s.time
                FROM appointment a
                JOIN schedule s ON a.schedule_id = s.id
                JOIN users u ON a.student_id = u.id
                WHERE s.coach_id = ?
                """;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, coachId);
                try (ResultSet rs = ps.executeQuery()) {
                    List<Map<String, String>> appointments = new ArrayList<>();
                    while (rs.next()) {
                        Map<String, String> appointment = new HashMap<>();
                        appointment.put("id", String.valueOf(rs.getInt("appointment_id")));
                        appointment.put("student_name", rs.getString("student_name"));
                        appointment.put("date", rs.getString("date"));
                        appointment.put("time", rs.getString("time"));
                        appointments.add(appointment);
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
