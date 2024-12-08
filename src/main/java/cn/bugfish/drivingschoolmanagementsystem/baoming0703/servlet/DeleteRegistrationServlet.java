package cn.bugfish.drivingschoolmanagementsystem.baoming0703.servlet;

import cn.bugfish.drivingschoolmanagementsystem.baoming0703.dao.StudentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/student/delete-registration")
public class DeleteRegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("\n====== 开始删除报名信息 ======");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            System.out.println("删除学生ID: " + studentId);

            StudentDao studentDao = new StudentDao();
            boolean success = studentDao.deleteStudent(studentId);

            System.out.println("删除结果: " + (success ? "成功" : "失败"));
            out.write("{\"success\":" + success + "}");

        } catch (Exception e) {
            System.err.println("删除报名信息时发生错误:");
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"success\":false,\"message\":\"删除失败\"}");
        }
    }
}
