package cn.bugfish.drivingschoolmanagementsystem.ExamSystem;

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

@WebServlet("/get-available-exams")
public class GetAvailableExamsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int studentId = (int) request.getSession().getAttribute("userid");
        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                SELECT c.*, (c.capacity - COUNT(cs.id)) AS remaining_capacity
                FROM exams c
                LEFT JOIN exam_selection cs ON c.id = cs.exam_id
                GROUP BY c.id
                HAVING remaining_capacity > 0
            """;
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                List<Map<String, Object>> exams = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> exam = new HashMap<>();
                    exam.put("id", rs.getInt("id"));
                    exam.put("exam_number", rs.getString("exam_number"));
                    exam.put("name", rs.getString("name"));
                    exam.put("start_time", rs.getString("start_time"));
                    exam.put("end_time", rs.getString("end_time"));
                    exam.put("remaining_capacity", rs.getInt("remaining_capacity"));
                    exams.add(exam);
                }
                String json = new Gson().toJson(Map.of("exams", exams));
                response.setContentType("application/json");
                response.getWriter().write(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
