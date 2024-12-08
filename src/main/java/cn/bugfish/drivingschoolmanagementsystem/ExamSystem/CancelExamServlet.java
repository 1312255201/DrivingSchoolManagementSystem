package cn.bugfish.drivingschoolmanagementsystem.ExamSystem;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/cancel-exam")
public class CancelExamServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int studentId = (int) request.getSession().getAttribute("userid");
        int examId = Integer.parseInt(request.getParameter("exam_id"));
        try (Connection conn = DBUtil.getConnection()) {
            System.out.println("qwq");
            String sql = "DELETE FROM exam_selection WHERE student_id = ? AND exam_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, studentId);
                ps.setInt(2, examId);
                ps.executeUpdate();
                response.getWriter().write("{\"success\": true, \"message\": \"取消成功！\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"取消失败！\"}");
        }
    }
}
