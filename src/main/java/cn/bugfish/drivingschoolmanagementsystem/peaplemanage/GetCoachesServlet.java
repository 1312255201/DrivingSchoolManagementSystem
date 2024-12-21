package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/get-coaches")
public class GetCoachesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (Connection conn = DBUtil.getConnection()) {
            // 从users表获取教练信息
            String sql = "SELECT id, name FROM users WHERE role = 'coach'"; // 假设用户表中有role字段标识教练
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                List<Map<String, Object>> coaches = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> coach = new HashMap<>();
                    coach.put("id", rs.getInt("id"));
                    coach.put("name", rs.getString("name"));
                    coaches.add(coach);
                }
                response.getWriter().write(new com.google.gson.Gson().toJson(coaches));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"获取教练信息失败！\"}");
        }
    }
}
