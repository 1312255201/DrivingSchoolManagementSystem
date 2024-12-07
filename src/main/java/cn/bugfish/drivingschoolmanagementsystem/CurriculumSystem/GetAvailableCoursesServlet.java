package cn.bugfish.drivingschoolmanagementsystem.CurriculumSystem;

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

@WebServlet("/get-available-courses")
public class GetAvailableCoursesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int studentId = (int) request.getSession().getAttribute("userid");
        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                SELECT c.*, (c.capacity - COUNT(cs.id)) AS remaining_capacity
                FROM courses c
                LEFT JOIN course_selection cs ON c.id = cs.course_id
                GROUP BY c.id
                HAVING remaining_capacity > 0
            """;
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                List<Map<String, Object>> courses = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> course = new HashMap<>();
                    course.put("id", rs.getInt("id"));
                    course.put("course_number", rs.getString("course_number"));
                    course.put("name", rs.getString("name"));
                    course.put("start_time", rs.getString("start_time"));
                    course.put("end_time", rs.getString("end_time"));
                    course.put("remaining_capacity", rs.getInt("remaining_capacity"));
                    courses.add(course);
                }
                String json = new Gson().toJson(Map.of("courses", courses));
                response.setContentType("application/json");
                response.getWriter().write(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
