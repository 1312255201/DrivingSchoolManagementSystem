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
import java.util.Map;

@WebServlet("/get-exams")
public class GetExamsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ArrayList<Map<String, Object>> exams = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM exams ";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> course = new HashMap<>();
                        course.put("id", rs.getInt("id"));
                        course.put("exam_number", rs.getString("exam_number"));
                        course.put("name", rs.getString("name"));
                        course.put("start_time", rs.getTimestamp("start_time").toString());
                        course.put("end_time", rs.getTimestamp("end_time").toString());
                        course.put("capacity", rs.getInt("capacity"));
                        course.put("content", rs.getString("content"));
                        exams.add(course);
                    }
                }
            }

            Gson gson = new Gson();
            String jsonResponse = gson.toJson(Map.of("success", true, "exams", exams));

            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"加载课程失败\"}");
        }
    }
}