package cn.bugfish.drivingschoolmanagementsystem.baoming0703.servlet;

import cn.bugfish.drivingschoolmanagementsystem.baoming0703.dao.StudentDao;
import cn.bugfish.drivingschoolmanagementsystem.baoming0703.po.Student;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/student/registration-review")
public class AuditStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("\n====== 开始处理获取学生列表请求 ======");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            String status = request.getParameter("status");
            System.out.println("请求参数 status: " + status);
            
            StudentDao studentDao = new StudentDao();
            List<Student> students = studentDao.getStudentsByStatus(status);
            
            System.out.println("查询到的学生数量: " + students.size());
            for (Student student : students) {
                System.out.println("学生信息: " + student);
            }
            
            // 使用Gson转换为JSON
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(students);
            System.out.println("返回的JSON数据: " + jsonResponse);
            
            out.write(jsonResponse);
            
        } catch (Exception e) {
            System.err.println("处理请求时发生错误:");
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"error\":\"获取数据失败\"}");
        } finally {
            System.out.println("====== 处理获取学生列表请求结束 ======\n");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("\n====== 开始处理审核请求 ======");
        
        try {
            // 获取请求参数
            int studentId = Integer.parseInt(request.getParameter("studentId"));
            String status = request.getParameter("status");
            String rejectReason = request.getParameter("rejectReason");
            String currentStatus = request.getParameter("currentStatus");
            String reviewerId = request.getParameter("reviewerId");

            System.out.println("审核请求参数:");
            System.out.println("studentId: " + studentId);
            System.out.println("status: " + status);
            System.out.println("rejectReason: " + rejectReason);
            System.out.println("currentStatus: " + currentStatus);
            System.out.println("reviewerId: " + reviewerId);

            // 如果当前状态是待审核，才允许审核
            if ("PENDING".equals(currentStatus)) {
                StudentDao studentDao = new StudentDao();
                boolean success;
                
                if ("APPROVED".equals(status)) {
                    success = studentDao.updateStudentStatusToApproved(studentId, reviewerId);
                } else if ("REJECTED".equals(status)) {
                    success = studentDao.updateStudentStatusToRejected(studentId, rejectReason, reviewerId);
                } else {
                    success = false;
                    System.out.println("无效的审核状态: " + status);
                }

                System.out.println("审核操作结果: " + (success ? "成功" : "失败"));
                
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"success\":" + success + "}");
            } else {
                System.out.println("不能审核已审核的学生，当前状态: " + currentStatus);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\":\"不能审核已审核的学生\"}");
            }
            
        } catch (Exception e) {
            System.err.println("处理审核请求时发生错误:");
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"审核失败\"}");
        } finally {
            System.out.println("====== 处理审核请求结束 ======\n");
        }
    }
}
