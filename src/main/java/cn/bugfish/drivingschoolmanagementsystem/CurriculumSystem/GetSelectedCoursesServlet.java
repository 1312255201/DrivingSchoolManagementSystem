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

@WebServlet("/get-selected-courses")
public class GetSelectedCoursesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int studentId = (int) request.getSession().getAttribute("userid"); // 从会话中获取学生ID

        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                SELECT c.id, c.course_number, c.name, c.start_time, c.end_time
                FROM courses c
                INNER JOIN course_selection cs ON c.id = cs.course_id
                WHERE cs.student_id = ?
            """;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, studentId);

                try (ResultSet rs = ps.executeQuery()) {
                    List<Map<String, Object>> courses = new ArrayList<>();
                    while (rs.next()) {
                        Map<String, Object> course = new HashMap<>();
                        course.put("id", rs.getInt("id"));
                        course.put("course_number", rs.getString("course_number"));
                        course.put("name", rs.getString("name"));
                        course.put("start_time", rs.getString("start_time"));
                        course.put("end_time", rs.getString("end_time"));
                        courses.add(course);
                    }
                    // 将数据转换为JSON格式
                    String json = new Gson().toJson(Map.of("courses", courses));
                    response.setContentType("application/json");
                    response.getWriter().write(json);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"加载失败，请稍后重试！\"}");
        }
    }
}