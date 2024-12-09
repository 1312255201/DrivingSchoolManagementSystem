package cn.bugfish.drivingschoolmanagementsystem.ExamSystem;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/exam-search")
public class ExamSearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String searchQuery = request.getParameter("query");
        List<String> exams = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT id, exam_number, name, start_time, end_time, remaining_capacity FROM exams WHERE name LIKE ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "%" + searchQuery + "%");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String exam = "{"
                                + "\"id\":" + rs.getInt("id") + ","
                                + "\"exam_number\":\"" + rs.getString("exam_number") + "\","
                                + "\"name\":\"" + rs.getString("name") + "\","
                                + "\"start_time\":\"" + rs.getString("start_time") + "\","
                                + "\"end_time\":\"" + rs.getString("end_time") + "\","
                                + "\"remaining_capacity\":" + rs.getInt("remaining_capacity")
                                + "}";
                        exams.add(exam);
                    }
                }
            }

            String json = "{\"exams\": [" + String.join(",", exams) + "]}";

            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"查询失败！\"}");
        }
    }
}