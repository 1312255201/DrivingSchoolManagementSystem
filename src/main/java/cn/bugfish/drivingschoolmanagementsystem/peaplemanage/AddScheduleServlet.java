package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;
import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/add-schedule")
public class AddScheduleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int coachId = (int) request.getSession().getAttribute("userid"); // 教练ID从会话中获取
        String date = request.getParameter("date");
        String time = request.getParameter("time");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO schedule (coach_id, date, time) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, coachId);
                ps.setString(2, date);
                ps.setString(3, time);
                ps.executeUpdate();
                response.getWriter().write("{\"success\": true}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false}");
        }
    }
}
