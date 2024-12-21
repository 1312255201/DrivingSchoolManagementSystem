package cn.bugfish.drivingschoolmanagementsystem.peaplemanage;
import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/pay-salary")
public class PaySalaryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int adminId = (int) request.getSession().getAttribute("userid"); // 管理员ID
        int coachId = Integer.parseInt(request.getParameter("coach_id"));
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));
        String bankCard = request.getParameter("bankcard"); // 获取银行卡号
        String note = request.getParameter("note");

        response.setContentType("application/json");
        try (Connection conn = DBUtil.getConnection()) {
            // 插入工资支付记录
            String sql = "INSERT INTO salary_payments (coach_id, amount, admin_id, bank_card, note) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, coachId);
                ps.setBigDecimal(2, amount);
                ps.setInt(3, adminId);
                ps.setString(4, bankCard);
                ps.setString(5, note);
                ps.executeUpdate();
                response.getWriter().write("{\"success\": true, \"message\": \"工资发放成功！\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("{\"success\": false, \"message\": \"工资发放失败！\"}");
        }
    }
}
