package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import cn.bugfish.drivingschoolmanagementsystem.DataBase.UserDao;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;


@WebServlet("/AssignStudentServletChange")
public class AssignStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int coachId = Integer.parseInt(request.getParameter("coach_id"));
        int studentId = Integer.parseInt(request.getParameter("student_id"));
        String teachLevel = request.getParameter("teach_level");

        UserDao userDao = new UserDao();

        // 检查是否已分配相同科目
        if (userDao.isStudentAssignedToSubject(studentId, teachLevel)) {
            response.getWriter().write("已经分配相同科目了");
            return;
        }

        // 检查是否重复绑定同一教练同一科目
        if (userDao.isDuplicateAssignment(coachId, studentId, teachLevel)) {
            response.getWriter().write("重复绑定");
            return;
        }

        // 插入关系
        boolean success = userDao.assignStudentToCoach(coachId, studentId, teachLevel);
        if (success) {
            response.getWriter().write("绑定成功");
        } else {
            response.getWriter().write("失败");
        }
    }



}