package cn.bugfish.drivingschoolmanagementsystem.fee0709.po;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Refund {
    private int refundId;
    private int paymentId;
    private BigDecimal refundAmount;
    private String refundReason;
    private String refundStatus;
    private Timestamp refundTime;

    // 额外字段用于显示
    private String studentId;
    private String feeType;
    private String originalAmount;

    public Refund() {

    }
}
