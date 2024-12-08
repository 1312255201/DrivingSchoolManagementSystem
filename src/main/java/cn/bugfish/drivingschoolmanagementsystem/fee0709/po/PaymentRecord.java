package cn.bugfish.drivingschoolmanagementsystem.fee0709.po;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class PaymentRecord {
    private int paymentId;
    private int studentId;
    private int feeId;
    private BigDecimal amount;
    private Timestamp paymentTime;
    private String paymentMethod;
    private String paymentStatus;
    private String feeType; // 用于显示费用类型

    public PaymentRecord() {
    }
}