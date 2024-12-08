package cn.bugfish.drivingschoolmanagementsystem.fee0709.dao;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import cn.bugfish.drivingschoolmanagementsystem.fee0709.po.Refund;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RefundDAO {
    private Connection connection;

    public RefundDAO() throws SQLException {
        this.connection = DBUtil.getConnection();
    }

    public boolean createRefund(Refund refund) throws SQLException {
        String sql = "INSERT INTO refunds (payment_id, refund_amount, refund_reason, refund_status) " +
                    "VALUES (?, ?, ?, '处理中')";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, refund.getPaymentId());
            stmt.setBigDecimal(2, refund.getRefundAmount());
            stmt.setString(3, refund.getRefundReason());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        refund.setRefundId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public List<Refund> getAllRefunds() throws SQLException {
        List<Refund> refunds = new ArrayList<>();
        String sql = "SELECT r.*, pr.student_id, f.fee_type, pr.amount as original_amount " +
                    "FROM refunds r " +
                    "JOIN payment_records pr ON r.payment_id = pr.payment_id " +
                    "JOIN fees f ON pr.fee_id = f.fee_id " +
                    "ORDER BY r.refund_time DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Refund refund = mapResultSetToRefund(rs);
                refunds.add(refund);
            }
        }
        return refunds;
    }

    private Refund mapResultSetToRefund(ResultSet rs) throws SQLException {
        Refund refund = new Refund();
        refund.setRefundId(rs.getInt("refund_id"));
        refund.setPaymentId(rs.getInt("payment_id"));
        refund.setRefundAmount(rs.getBigDecimal("refund_amount"));
        refund.setRefundReason(rs.getString("refund_reason"));
        refund.setRefundStatus(rs.getString("refund_status"));
        refund.setRefundTime(rs.getTimestamp("refund_time"));
        refund.setStudentId(rs.getString("student_id"));
        refund.setFeeType(rs.getString("fee_type"));
        refund.setOriginalAmount(rs.getString("original_amount"));
        return refund;
    }

    public List<Refund> getRefundsByPaymentId(int paymentId) throws SQLException {
        List<Refund> refunds = new ArrayList<>();
        String sql = "SELECT r.*, pr.student_id, f.fee_type, pr.amount as original_amount " +
                    "FROM refunds r " +
                    "JOIN payment_records pr ON r.payment_id = pr.payment_id " +
                    "JOIN fees f ON pr.fee_id = f.fee_id " +
                    "WHERE r.payment_id = ? " +
                    "ORDER BY r.refund_time DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, paymentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Refund refund = mapResultSetToRefund(rs);
                    refunds.add(refund);
                }
            }
        }
        return refunds;
    }
}