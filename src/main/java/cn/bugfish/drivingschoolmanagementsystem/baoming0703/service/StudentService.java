package cn.bugfish.drivingschoolmanagementsystem.baoming0703.service;


import cn.bugfish.drivingschoolmanagementsystem.baoming0703.dao.StudentDao;
import cn.bugfish.drivingschoolmanagementsystem.baoming0703.dao.StudentStateDao;
import cn.bugfish.drivingschoolmanagementsystem.baoming0703.po.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private StudentDao studentDao;
    private StudentStateDao studentStateDao;

    public StudentService() {
        this.studentDao = new StudentDao();
        System.out.println("StudentService 已初始化");
    }

    public boolean registerStudent(Student student, String state, String studyType) {
        try {
            System.out.println("\n开始注册学员:");
            System.out.println("学员信息: " + student);
            System.out.println("状态: " + state);
            System.out.println("学习类型: " + studyType);

            // 保存学员基本信息
            boolean saveSuccess = studentDao.saveStudent(student);
            if (!saveSuccess) {
                System.out.println("保存学员基本信息失败");
                return false;
            }
            System.out.println("学员注册成功");
            return true;
        } catch (Exception e) {
            System.err.println("注册学员时发生错误:");
            e.printStackTrace();
            return false;
        }
    }

    // 获取学员信息
    public Student getStudentById(int studentId) {
        try {
            return studentDao.getStudentById(studentId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 获取待审核的报名列表
    public List<Student> getPendingRegistrations() {
        return studentDao.getStudentsByStatus("PENDING");
    }

    // 获取指定状态的报名列表
    public List<Student> getStudentsByStatus(String status) {
        return studentDao.getStudentsByStatus(status);
    }

    // 审核通过
    public boolean approveRegistration(int studentId, String reviewerId) {
        return studentDao.updateRegistrationStatus(studentId, "APPROVED", null, reviewerId);
    }

    // 审核拒绝
    public boolean rejectRegistration(int studentId, String rejectReason, String reviewerId) {
        if (rejectReason == null || rejectReason.trim().isEmpty()) {
            throw new IllegalArgumentException("拒绝原因不能为空");
        }
        return studentDao.updateRegistrationStatus(studentId, "REJECTED", rejectReason, reviewerId);
    }
}