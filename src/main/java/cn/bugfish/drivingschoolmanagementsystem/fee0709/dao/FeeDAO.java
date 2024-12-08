package cn.bugfish.drivingschoolmanagementsystem.fee0709.dao;

import cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil;
import cn.bugfish.drivingschoolmanagementsystem.fee0709.po.Fee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeeDAO {
    private Connection connection;

    // 使用 DBUtil 获取数据库连接
    public FeeDAO() throws SQLException {
        // 获取数据库连接
        this.connection = DBUtil.getConnection();
    }

    // 添加费用
    public void addFee(Fee fee) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "INSERT INTO fees (fee_type, amount, is_installment, installment_count, payment_options, created_at) " +
                    "VALUES (?, ?, ?, ?, CAST(? AS JSON), ?)";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fee.getFeeType());
            stmt.setBigDecimal(2, fee.getAmount());
            stmt.setBoolean(3, fee.isInstallment());
            stmt.setInt(4, fee.getInstallmentCount());
            
            // 将支付方式转换为JSON格式
            String jsonPaymentOptions = "[\"" + fee.getPaymentOptions() + "\"]";
            stmt.setString(5, jsonPaymentOptions);
            
            stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));

            System.out.println("执行SQL: " + sql);
            System.out.println("参数值:");
            System.out.println("feeType: " + fee.getFeeType());
            System.out.println("amount: " + fee.getAmount());
            System.out.println("isInstallment: " + fee.isInstallment());
            System.out.println("installmentCount: " + fee.getInstallmentCount());
            System.out.println("paymentOptions JSON: " + jsonPaymentOptions);

            int result = stmt.executeUpdate();
            if (result != 1) {
                throw new SQLException("插入费用记录失败");
            }
        } catch (SQLException e) {
            System.err.println("SQL执行错误: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Fee> getAllFees() throws SQLException {
        List<Fee> fees = new ArrayList<>();
        String sql = "SELECT * FROM fees ORDER BY created_at DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Fee fee = new Fee(
                    rs.getInt("fee_id"),
                    rs.getString("fee_type"),
                    rs.getBigDecimal("amount"),
                    rs.getBoolean("is_installment"),
                    rs.getInt("installment_count"),
                    rs.getString("payment_options"),
                    rs.getTimestamp("created_at")
                );
                fees.add(fee);
            }
        }
        return fees;
    }

    public Fee getFeeById(int feeId) throws SQLException {
        String sql = "SELECT * FROM fees WHERE fee_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, feeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Fee(
                        rs.getInt("fee_id"),
                        rs.getString("fee_type"),
                        rs.getBigDecimal("amount"),
                        rs.getBoolean("is_installment"),
                        rs.getInt("installment_count"),
                        rs.getString("payment_options"),
                        rs.getTimestamp("created_at")
                    );
                }
            }
        }
        return null;
    }

    public boolean updateFee(Fee fee) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "UPDATE fees SET fee_type = ?, amount = ?, is_installment = ?, " +
                        "installment_count = ?, payment_options = CAST(? AS JSON), created_at = ? WHERE fee_id = ?";
                        
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fee.getFeeType());
            stmt.setBigDecimal(2, fee.getAmount());
            stmt.setBoolean(3, fee.isInstallment());
            stmt.setInt(4, fee.getInstallmentCount());
            
            // 将支付方式转换为JSON格式
            String jsonPaymentOptions = "[\"" + fee.getPaymentOptions() + "\"]";
            stmt.setString(5, jsonPaymentOptions);
            
            stmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(7, fee.getId());
            
            System.out.println("执行的SQL: " + sql);
            System.out.println("payment_options JSON值: " + jsonPaymentOptions);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("更新费用时发生SQL错误: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 在 FeeDAO 类中添加删除方法
    public boolean deleteFee(int feeId) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = DBUtil.getConnection();
            String sql = "DELETE FROM fees WHERE fee_id = ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, feeId);
            
            System.out.println("执行删除SQL: " + sql);
            System.out.println("删除的费用ID: " + feeId);
            
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("删除费用时发生SQL错误: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 其他查询和更新操作...
}
