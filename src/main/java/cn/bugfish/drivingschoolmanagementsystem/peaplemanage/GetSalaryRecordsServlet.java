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

@WebServlet("/get-salary-records")
public class GetSalaryRecordsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT u.name AS coach_name, sp.amount, sp.payment_date, sp.note " +
                    "FROM salary_payments sp " +
                    "JOIN users u ON sp.coach_id = u.id " +
                    "ORDER BY sp.payment_date DESC";
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                List<Map<String, Object>> records = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> record = new HashMap<>();
                    record.put("coach_name", rs.getString("coach_name"));
                    record.put("amount", rs.getBigDecimal("amount").toString());
                    record.put("payment_date", rs.getTimestamp("payment_date"));
                    record.put("note", rs.getString("note"));
                    records.add(record);
                }
                response.getWriter().write(new com.google.gson.Gson().toJson(records));
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"获取工资发放记录失败！\"}");
        }
    }
}
