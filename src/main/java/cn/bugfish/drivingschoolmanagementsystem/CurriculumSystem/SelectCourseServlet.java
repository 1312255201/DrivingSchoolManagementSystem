package cn.bugfish.drivingschoolmanagementsystem.CurriculumSystem;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/select-course")
public class SelectCourseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int studentId = (int) request.getSession().getAttribute("userid");
        int courseId = Integer.parseInt(request.getParameter("course_id"));
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO course_selection (student_id, course_id) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, studentId);
                ps.setInt(2, courseId);
                ps.executeUpdate();
                response.getWriter().write("{\"success\": true, \"message\": \"选课成功！\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"选课失败！\"}");
        }
    }
}
