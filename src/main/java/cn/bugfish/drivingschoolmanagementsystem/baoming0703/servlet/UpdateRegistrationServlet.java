package cn.bugfish.drivingschoolmanagementsystem.baoming0703.servlet;

import cn.bugfish.drivingschoolmanagementsystem.baoming0703.dao.StudentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/student/update-registration")
public class UpdateRegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("\n====== 开始更新报名信息 ======");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");

            System.out.println("更新参数: studentId=" + studentId +
                    ", name=" + name +
                    ", phone=" + phone +
                    ", email=" + email);

            StudentDao studentDao = new StudentDao();
            boolean success = studentDao.updateStudentInfo(studentId, name, phone, email);

            System.out.println("更新结果: " + (success ? "成功" : "失败"));
            out.write("{\"success\":" + success + "}");

        } catch (Exception e) {
            System.err.println("更新报名信息时发生错误:");
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"success\":false,\"message\":\"更新失败\"}");
        }
    }
}