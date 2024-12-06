package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/RemoveStudentServlet")
public class RemoveStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int coachId = Integer.parseInt(request.getParameter("coach_id"));
        int studentId = Integer.parseInt(request.getParameter("student_id"));
        String teach_level = (String) request.getParameter("teach_level");
        String message = "";

        try {
            Connection conn = DBUtil.getConnection();

            // 删除绑定关系
            String deleteQuery = "DELETE FROM teach_info WHERE coach_id = ? AND student_id = ? AND teach_level = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
            deleteStmt.setInt(1, coachId);
            deleteStmt.setInt(2, studentId);
            deleteStmt.setString(3, teach_level);
            int rows = deleteStmt.executeUpdate();

            message = (rows > 0) ? "学员解绑成功！" : "学员解绑失败！";
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            message = "服务器错误！";
        }

        response.setContentType("text/plain");
        response.getWriter().write(message);
    }
}
