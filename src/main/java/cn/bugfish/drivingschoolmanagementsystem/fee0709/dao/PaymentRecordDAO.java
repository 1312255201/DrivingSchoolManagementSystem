package cn.bugfish.drivingschoolmanagementsystem.fee0709.dao;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import cn.bugfish.drivingschoolmanagementsystem.fee0709.po.PaymentRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentRecordDAO {
    private Connection connection;

    public PaymentRecordDAO() throws SQLException {
        this.connection = DBUtil.getConnection();
    }

    public List<PaymentRecord> getAllPaymentRecords() throws SQLException {
        List<PaymentRecord> records = new ArrayList<>();
        String sql = "SELECT pr.*, f.fee_type FROM payment_records pr " +
                    "LEFT JOIN fees f ON pr.fee_id = f.fee_id " +
                    "ORDER BY pr.payment_time DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                PaymentRecord record = mapResultSetToPaymentRecord(rs);
                records.add(record);
            }
        }
        return records;
    }

    public List<PaymentRecord> getPaymentRecordsByStudentId(int studentId) throws SQLException {
        List<PaymentRecord> records = new ArrayList<>();
        String sql = "SELECT pr.*, f.fee_type FROM payment_records pr " +
                    "LEFT JOIN fees f ON pr.fee_id = f.fee_id " +
                    "WHERE pr.student_id = ? " +
                    "ORDER BY pr.payment_time DESC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    PaymentRecord record = mapResultSetToPaymentRecord(rs);
                    records.add(record);
                }
            }
        }
        return records;
    }

    private PaymentRecord mapResultSetToPaymentRecord(ResultSet rs) throws SQLException {
        PaymentRecord record = new PaymentRecord();
        record.setPaymentId(rs.getInt("payment_id"));
        record.setStudentId(rs.getInt("student_id"));
        record.setFeeId(rs.getInt("fee_id"));
        record.setAmount(rs.getBigDecimal("amount"));
        record.setPaymentTime(rs.getTimestamp("payment_time"));
        record.setPaymentMethod(rs.getString("payment_method"));
        record.setPaymentStatus(rs.getString("payment_status"));
        record.setFeeType(rs.getString("fee_type"));
        return record;
    }

    public boolean createPaymentRecord(PaymentRecord record) throws SQLException {
        String sql = "INSERT INTO payment_records (student_id, fee_id, amount, payment_method, payment_status) " +
                    "VALUES (?, ?, ?, ?, ?)";
                    
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, record.getStudentId());
            stmt.setInt(2, record.getFeeId());
            stmt.setBigDecimal(3, record.getAmount());
            stmt.setString(4, record.getPaymentMethod());
            stmt.setString(5, "成功"); // 默认设置为成功
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        record.setPaymentId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public PaymentRecord getPaymentRecordById(int paymentId) throws SQLException {
        String sql = "SELECT pr.*, f.fee_type FROM payment_records pr " +
                    "LEFT JOIN fees f ON pr.fee_id = f.fee_id " +
                    "WHERE pr.payment_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, paymentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPaymentRecord(rs);
                }
            }
        }
        return null;
    }
}
