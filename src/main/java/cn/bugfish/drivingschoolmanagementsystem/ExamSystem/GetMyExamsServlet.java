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

@WebServlet("/get-my-exams")
public class GetMyExamsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int coachId = (int) request.getSession().getAttribute("userid");
        ArrayList<Map<String, Object>> exams = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM exams WHERE coach_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, coachId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Map<String, Object> exam = new HashMap<>();
                        exam.put("id", rs.getInt("id"));
                        exam.put("exam_number", rs.getString("exam_number"));
                        exam.put("name", rs.getString("name"));
                        exam.put("start_time", rs.getTimestamp("start_time").toString());
                        exam.put("end_time", rs.getTimestamp("end_time").toString());
                        exam.put("capacity", rs.getInt("capacity"));
                        exam.put("content", rs.getString("content"));
                        exams.add(exam);
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
            response.getWriter().write("{\"success\": false, \"message\": \"加载考试失败\"}");
        }
    }
}