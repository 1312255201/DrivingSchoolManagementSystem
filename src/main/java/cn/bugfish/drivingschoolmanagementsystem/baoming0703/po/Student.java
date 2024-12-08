package cn.bugfish.drivingschoolmanagementsystem.baoming0703.po;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Student {
    private int studentId;
    private String userId;
    private String name;
    private String phone;
    private String email;
    private Timestamp createdAt;
    private String createdAtFormatted;
    private String registrationStatus; // 报名状态：PENDING(待审核), APPROVED(已通过), REJECTED(已拒绝)
    private String rejectReason;      // 拒绝原因
    private Timestamp reviewTime;      // 审核时间
    private String reviewerId;         // 审核人ID

    public Student() {
        this.registrationStatus = "PENDING"; // 默认状态为待审核
    }
}