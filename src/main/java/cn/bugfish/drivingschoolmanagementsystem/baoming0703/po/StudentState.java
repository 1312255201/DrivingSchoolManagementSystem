package cn.bugfish.drivingschoolmanagementsystem.baoming0703.po;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentState { 
    private int id;
    private int studentId;
    private String state;
    private String studyType;

    public StudentState() {
    }
}