package cn.bugfish.drivingschoolmanagementsystem.baoming0703.servlet;

import cn.bugfish.drivingschoolmanagementsystem.baoming0703.dao.StudentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/student/registration-review/approve")
public class ApproveRegistrationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int studentId = Integer.parseInt(request.getParameter("studentId"));
        String reviewerId = request.getParameter("reviewerId");

        StudentDao studentDao = new StudentDao();
        boolean success = studentDao.updateStudentStatusToApproved(studentId, reviewerId);

        // 设置响应为 JSON 格式
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 返回操作结果
        String jsonResponse = "{\"success\": " + success + "}";
        response.getWriter().write(jsonResponse);
    }
}
