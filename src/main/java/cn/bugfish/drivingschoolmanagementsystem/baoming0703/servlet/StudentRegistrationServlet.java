package cn.bugfish.drivingschoolmanagementsystem.baoming0703.servlet;

import cn.bugfish.drivingschoolmanagementsystem.baoming0703.dao.StudentDao;
import cn.bugfish.drivingschoolmanagementsystem.baoming0703.po.Student;
import cn.bugfish.drivingschoolmanagementsystem.baoming0703.service.StudentService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/submitRegistration")
public class StudentRegistrationServlet extends HttpServlet {
    private StudentService studentService;

    @Override
    public void init() throws ServletException {
        this.studentService = new StudentService();
        System.out.println("StudentRegistrationServlet 已初始化");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("\n====== 开始处理报名请求 ======");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // 从session获取当前登录用户的ID
            HttpSession session = request.getSession();
            Integer userId = (Integer) session.getAttribute("userid");

            if (userId == null) {
                System.out.println("用户未登录");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                out.write("{\"success\":false,\"message\":\"请先登录\"}");
                return;
            }

            // 检查是否已经报名
            StudentDao studentDao = new StudentDao();
            Student existingStudent = studentDao.getStudentByUserId(String.valueOf(userId));

            if (existingStudent != null && !"DELETED".equals(existingStudent.getRegistrationStatus())) {
                System.out.println("用户已报名，状态: " + existingStudent.getRegistrationStatus());
                String message;
                switch (existingStudent.getRegistrationStatus()) {
                    case "PENDING":
                        message = "您已提交报名申请，正在等待审核";
                        break;
                    case "APPROVED":
                        message = "您的报名申请已通过审核";
                        break;
                    case "REJECTED":
                        message = "您的报名申请已被拒绝，如需重新报名请先删除原申请";
                        break;
                    default:
                        message = "您已经报名，不能重复报名";
                }
                response.setStatus(HttpServletResponse.SC_CONFLICT);
                out.write("{\"success\":false,\"message\":\"" + message + "\"}");
                return;
            }

            // 获取表单数据
            String name = request.getParameter("student_name");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");

            // 验证数据
            if (name == null || name.trim().isEmpty() ||
                    phone == null || phone.trim().isEmpty() ||
                    email == null || email.trim().isEmpty()) {

                System.out.println("表单数据不完整");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"success\":false,\"message\":\"请填写完整信息\"}");
                return;
            }

            // 创建学生对象
            Student student = new Student();
            student.setUserId(String.valueOf(userId));
            student.setName(name);
            student.setPhone(phone);
            student.setEmail(email);
            student.setRegistrationStatus("PENDING");

            // 保存到数据库
            boolean success = studentDao.saveStudent(student);

            if (success) {
                System.out.println("报名成功，学生ID: " + student.getStudentId());
                out.write("{\"success\":true,\"message\":\"报名成功，请等待审核\"}");
            } else {
                System.out.println("报名失败");
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.write("{\"success\":false,\"message\":\"报名失败，请稍后重试\"}");
            }

        } catch (Exception e) {
            System.err.println("处理报名请求时发生错误:");
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.write("{\"success\":false,\"message\":\"系统错误，请稍后重试\"}");
        }
    }
}