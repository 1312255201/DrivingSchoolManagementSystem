package cn.bugfish.drivingschoolmanagementsystem.fee0707.service;


import cn.bugfish.drivingschoolmanagementsystem.fee0707.dao.PaymentRecordDAO;
import cn.bugfish.drivingschoolmanagementsystem.fee0707.po.PaymentRecord;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PaymentRecordService {
    private PaymentRecordDAO paymentRecordDAO;

    public PaymentRecordService() {
        try {
            this.paymentRecordDAO = new PaymentRecordDAO();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("初始化PaymentRecordDAO失败", e);
        }
    }

    public List<PaymentRecord> getAllPaymentRecords() {
        try {
            return paymentRecordDAO.getAllPaymentRecords();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<PaymentRecord> getPaymentRecordsByStudentId(int studentId) {
        try {
            return paymentRecordDAO.getPaymentRecordsByStudentId(studentId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean createPaymentRecord(int studentId, int feeId, BigDecimal amount, String paymentMethod) {
        try {
            PaymentRecord record = new PaymentRecord();
            record.setStudentId(studentId);
            record.setFeeId(feeId);
            record.setAmount(amount);
            record.setPaymentMethod(paymentMethod);
            record.setPaymentTime(new Timestamp(System.currentTimeMillis()));
            
            return paymentRecordDAO.createPaymentRecord(record);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
