package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;
@WebServlet("/GetStudentsServlet")
public class GetStudentsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int coachId = Integer.parseInt(request.getParameter("coach_id"));
        List<Map<String, Object>> students = new ArrayList<>();

        try {
            Connection conn = DBUtil.getConnection();
            String query = "SELECT u.id, u.name FROM teach_info t " +
                    "JOIN users u ON t.student_id = u.id WHERE t.coach_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, coachId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, Object> student = new HashMap<>();
                student.put("id", rs.getInt("id"));
                student.put("name", rs.getString("name"));
                students.add(student);
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(new com.google.gson.Gson().toJson(students));
        out.flush();
    }
}