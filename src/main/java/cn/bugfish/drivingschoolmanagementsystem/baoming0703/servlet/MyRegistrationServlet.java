package cn.bugfish.drivingschoolmanagementsystem.baoming0703.servlet;

import cn.bugfish.drivingschoolmanagementsystem.baoming0703.dao.StudentDao;
import cn.bugfish.drivingschoolmanagementsystem.baoming0703.po.Student;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/student/my-registration")
public class MyRegistrationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("\n====== 开始获取个人报名信息 ======");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // 从session获取用户ID
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userid");
            
            System.out.println("当前用户ID: " + userId);
            System.out.println("Session ID: " + session.getId());
            System.out.println("Session 属性:");
            java.util.Enumeration<String> attrs = session.getAttributeNames();
            while (attrs.hasMoreElements()) {
                String name = attrs.nextElement();
                System.out.println("  " + name + " = " + session.getAttribute(name));
            }
            
            if (userId == null) {
                System.out.println("用户未登录");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.write("{\"error\":\"请先登录\"}");
                return;
            }

            StudentDao studentDao = new StudentDao();
            Student student = studentDao.getStudentByUserId(String.valueOf(userId));
            
            if (student != null) {
                System.out.println("找到学生信息: " + student);
                Gson gson = new Gson();
                out.write(gson.toJson(student));
            } else {
                System.out.println("未找到学生信息");
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.write("{\"error\":\"未找到报名信息\"}");
            }
            
        } catch (Exception e) {
            System.err.println("获取报名信息时发生错误:");
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\":\"获取数据失败\"}");
        }
    }
}