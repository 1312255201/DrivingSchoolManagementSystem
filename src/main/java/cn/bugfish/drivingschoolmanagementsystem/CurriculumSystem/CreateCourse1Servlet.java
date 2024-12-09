package cn.bugfish.drivingschoolmanagementsystem.CurriculumSystem;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/create-course1")
public class CreateCourse1Servlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String courseNumber = request.getParameter("course_number");
        String name = request.getParameter("name");
        String startTime = request.getParameter("start_time");
        String endTime = request.getParameter("end_time");
        int capacity = Integer.parseInt(request.getParameter("capacity"));
        String content = request.getParameter("content");
        int coachId = (int) request.getSession().getAttribute("userid"); // 从会话中获取教练ID

        try (Connection conn = DBUtil.getConnection()) {
            String sql = """
                INSERT INTO courses (course_number, name, start_time, end_time, capacity, content, coach_id)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, courseNumber);
                ps.setString(2, name);
                ps.setString(3, startTime);
                ps.setString(4, endTime);
                ps.setInt(5, capacity);
                ps.setString(6, content);
                ps.setInt(7, coachId);
                ps.executeUpdate();
            }

            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true, \"message\": \"课程创建成功！\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"课程创建失败，请稍后重试！\"}");
        }
    }
}