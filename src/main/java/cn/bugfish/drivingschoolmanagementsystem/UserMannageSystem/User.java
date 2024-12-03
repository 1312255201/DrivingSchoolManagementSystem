package cn.bugfish.drivingschoolmanagementsystem.UserMannageSystem;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;
    private String phonenumber;
    private String email;
    private String password;
    private LocalDateTime created_at;
}
