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
        String searchQuery = request.getParameter("query");  // 获取搜索关键词
        List<String> exams = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection()) {
            // 使用 LIKE 查询进行模糊搜索，查找包含搜索词的课程
            String sql = "SELECT id, exam_number, name, start_time, end_time, remaining_capacity FROM exams WHERE name LIKE ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "%" + searchQuery + "%");  // 模糊匹配课程名称
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        // 将查询到的课程数据直接添加到列表
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

            // 将课程列表转为 JSON 格式
            String json = "{\"exams\": [" + String.join(",", exams) + "]}";

            // 设置响应内容类型为 JSON
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"查询失败！\"}");
        }
    }
}