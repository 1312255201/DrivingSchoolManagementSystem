package cn.bugfish.drivingschoolmanagementsystem.fee0709.po;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Fee {
    private int id;
    private String feeType;
    private BigDecimal amount;
    private boolean isInstallment;
    private int installmentCount;
    private String paymentOptions;
    private Timestamp createdAt; // 添加创建时间字段

    public Fee() {

    }

}
