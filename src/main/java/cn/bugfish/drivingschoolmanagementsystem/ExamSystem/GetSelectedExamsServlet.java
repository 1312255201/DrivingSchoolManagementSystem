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

@WebServlet("/get-selected-exams")
public class GetSelectedExamsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int studentId = (int) request.getSession().getAttribute("userid"); // 从会话中获取学生ID

        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                SELECT c.id, c.exam_number, c.name, c.start_time, c.end_time
                FROM exams c
                INNER JOIN exam_selection cs ON c.id = cs.exam_id
                WHERE cs.student_id = ?
            """;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, studentId);

                try (ResultSet rs = ps.executeQuery()) {
                    List<Map<String, Object>> exams = new ArrayList<>();
                    while (rs.next()) {
                        Map<String, Object> exam = new HashMap<>();
                        exam.put("id", rs.getInt("id"));
                        exam.put("exam_number", rs.getString("exam_number"));
                        exam.put("name", rs.getString("name"));
                        exam.put("start_time", rs.getString("start_time"));
                        exam.put("end_time", rs.getString("end_time"));
                        exams.add(exam);
                    }
                    // 将数据转换为JSON格式
                    String json = new Gson().toJson(Map.of("exams", exams));
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