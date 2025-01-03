package cn.bugfish.drivingschoolmanagementsystem.fee0707.service;


import cn.bugfish.drivingschoolmanagementsystem.fee0707.dao.FeeDAO;
import cn.bugfish.drivingschoolmanagementsystem.fee0707.po.Fee;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FeeService {
    private FeeDAO feeDAO;

    public FeeService() {
        try {
            this.feeDAO = new FeeDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("初始化FeeDAO失败", e);
        }
    }

    public boolean addFee(String feeType, BigDecimal amount, boolean isInstallment,
                          int installmentCount, String paymentOptions) {
        try {
            // 参数验证
            if (feeType == null || feeType.trim().isEmpty()) {
                throw new IllegalArgumentException("费用类型不能为空");
            }
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("金额必须大于0");
            }
            if (isInstallment && installmentCount < 2) {
                throw new IllegalArgumentException("分期期数必须大于1");
            }

            Fee fee = new Fee();
            fee.setFeeType(feeType);
            fee.setAmount(amount);
            fee.setInstallment(isInstallment);
            fee.setInstallmentCount(installmentCount);
            fee.setPaymentOptions(paymentOptions);
            fee.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            feeDAO.addFee(fee);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Fee> getAllFees() {
        try {
            return feeDAO.getAllFees();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Fee getFeeById(int feeId) {
        try {
            return feeDAO.getFeeById(feeId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateFee(int feeId, String feeType, BigDecimal amount, 
                           boolean isInstallment, int installmentCount, String paymentOptions) {
        try {
            // 参数验证
            if (feeId <= 0) {
                throw new IllegalArgumentException("无效的费用ID");
            }
            if (feeType == null || feeType.trim().isEmpty()) {
                throw new IllegalArgumentException("费用类型不能为空");
            }
            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("金额必须大于0");
            }
            if (isInstallment && installmentCount < 2) {
                throw new IllegalArgumentException("分期期数必须大于1");
            }

            // 创建Fee对象
            Fee fee = new Fee();
            fee.setId(feeId);
            fee.setFeeType(feeType);
            fee.setAmount(amount);
            fee.setInstallment(isInstallment);
            fee.setInstallmentCount(installmentCount);
            fee.setPaymentOptions(paymentOptions);
            fee.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            // 调用DAO层更新数据
            return feeDAO.updateFee(fee);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFee(int feeId) {
        try {
            if (feeId <= 0) {
                throw new IllegalArgumentException("无效的费用ID");
            }
            return feeDAO.deleteFee(feeId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}