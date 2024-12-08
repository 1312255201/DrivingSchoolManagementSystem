package cn.bugfish.drivingschoolmanagementsystem.fee0709.service;


import cn.bugfish.drivingschoolmanagementsystem.fee0709.dao.PaymentRecordDAO;
import cn.bugfish.drivingschoolmanagementsystem.fee0709.dao.RefundDAO;
import cn.bugfish.drivingschoolmanagementsystem.fee0709.po.PaymentRecord;
import cn.bugfish.drivingschoolmanagementsystem.fee0709.po.Refund;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RefundService {
    private RefundDAO refundDAO;

    public RefundService() {
        try {
            this.refundDAO = new RefundDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("初始化RefundDAO失败", e);
        }
    }

    public boolean createRefund(int paymentId, BigDecimal refundAmount, String refundReason) {
        try {
            // 验证支付记录是否存在
            PaymentRecordDAO paymentRecordDAO = new PaymentRecordDAO();
            PaymentRecord payment = paymentRecordDAO.getPaymentRecordById(paymentId);
            
            if (payment == null) {
                System.out.println("支付记录不存在: " + paymentId);
                return false;
            }

            // 验证退款金额是否超过原支付金额
            if (refundAmount.compareTo(payment.getAmount()) > 0) {
                System.out.println("退款金额超过原支付金额");
                return false;
            }

            // 验证是否已经有退款申请
            List<Refund> existingRefunds = refundDAO.getRefundsByPaymentId(paymentId);
            if (!existingRefunds.isEmpty()) {
                System.out.println("该支付记录已存在退款申请");
                return false;
            }

            // 创建退款记录
            Refund refund = new Refund();
            refund.setPaymentId(paymentId);
            refund.setRefundAmount(refundAmount);
            refund.setRefundReason(refundReason);
            
            return refundDAO.createRefund(refund);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Refund> getAllRefunds() {
        try {
            return refundDAO.getAllRefunds();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}