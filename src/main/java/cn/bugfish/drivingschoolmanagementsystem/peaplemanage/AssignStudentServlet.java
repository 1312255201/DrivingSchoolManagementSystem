package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;


@WebServlet("/AssignStudentServletChange")
public class AssignStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var info =  request.getParameter("coach_id");
        int coachId = Integer.parseInt(request.getParameter("coach_id"));
        int studentId = Integer.parseInt(request.getParameter("student_id"));
        String message = "";

        try {
            Connection conn = DBUtil.getConnection();

            // 检查是否已绑定
            String checkQuery = "SELECT COUNT(*) FROM teach_info WHERE coach_id = ? AND student_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, coachId);
            checkStmt.setInt(2, studentId);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();

            if (rs.getInt(1) > 0) {
                message = "学员已绑定该教练！";
            } else {
                // 插入新绑定关系
                String insertQuery = "INSERT INTO teach_info (coach_id, student_id) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setInt(1, coachId);
                insertStmt.setInt(2, studentId);
                int rows = insertStmt.executeUpdate();

                message = (rows > 0) ? "学员绑定成功！" : "学员绑定失败！";
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            message = "服务器错误！";
        }

        response.setContentType("text/plain");
        response.getWriter().write(message);
    }
}