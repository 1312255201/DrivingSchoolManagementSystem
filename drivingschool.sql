/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 50726 (5.7.26-log)
 Source Host           : localhost:3306
 Source Schema         : drivingschool

 Target Server Type    : MySQL
 Target Server Version : 50726 (5.7.26-log)
 File Encoding         : 65001

 Date: 09/12/2024 03:03:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for appointment
-- ----------------------------
DROP TABLE IF EXISTS `appointment`;
CREATE TABLE `appointment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL COMMENT '学生id\r\n',
  `schedule_id` int(11) NOT NULL COMMENT '预约时间的唯一标识id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `student_schedule`(`student_id`, `schedule_id`) USING BTREE COMMENT '将studentid 和scheduleid 一起作为唯一索引，可以确保不会插入相同预约信息到数据库，将操作给数据库，减少后端代码。'
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用于记录预约对应关系表\r\n表设计人，软件22-7王赟昊' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for course_selection
-- ----------------------------
DROP TABLE IF EXISTS `course_selection`;
CREATE TABLE `course_selection`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL COMMENT '学生ID',
  `course_id` int(11) NOT NULL COMMENT '课程ID',
  `selected_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '选课时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `kf_studentandcourse`(`student_id`, `course_id`) USING BTREE COMMENT '防止重复选课',
  INDEX `fk_course`(`course_id`) USING BTREE,
  CONSTRAINT `fk_course` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_student` FOREIGN KEY (`student_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生选课记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for courses
-- ----------------------------
DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `course_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程编号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '课程名称',
  `start_time` datetime NOT NULL COMMENT '选课开始时间',
  `end_time` datetime NOT NULL COMMENT '选课结束时间',
  `capacity` int(11) NOT NULL COMMENT '可容纳人数',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '教学内容',
  `coach_id` int(11) NOT NULL COMMENT '教练ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `coach_id`(`coach_id`) USING BTREE,
  CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`coach_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '课程表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for email_config
-- ----------------------------
DROP TABLE IF EXISTS `email_config`;
CREATE TABLE `email_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识，主键',
  `email_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送邮箱地址',
  `email_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱的验证码',
  `smtp_host` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'smtp.qq.com' COMMENT 'smtp服务器地址',
  `smtp_port` int(11) NOT NULL DEFAULT 465 COMMENT 'smtp服务器端口',
  `is_ssl_enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否开启ssl安全验证',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表设计人软件22-7班王赟昊\r\n用于存储发送重置邮件的stmp授权密码，以及邮箱' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for exam_selection
-- ----------------------------
DROP TABLE IF EXISTS `exam_selection`;
CREATE TABLE `exam_selection`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL COMMENT '学生ID',
  `course_id` int(11) NOT NULL COMMENT '考试ID',
  `selected_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '考试时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_student`(`student_id`) USING BTREE,
  INDEX `fk_course`(`course_id`) USING BTREE,
  CONSTRAINT `exam_selection_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `exam_selection_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '学生考试记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for exams
-- ----------------------------
DROP TABLE IF EXISTS `exams`;
CREATE TABLE `exams`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '考试ID',
  `exam_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '考试编号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '考试名称',
  `start_time` datetime NOT NULL COMMENT '考试开始时间',
  `end_time` datetime NOT NULL COMMENT '考试结束时间',
  `capacity` int(11) NOT NULL COMMENT '可容考试人数',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '考试内容',
  `coach_id` int(11) NOT NULL COMMENT '教练ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `coach_id`(`coach_id`) USING BTREE,
  CONSTRAINT `exams_ibfk_1` FOREIGN KEY (`coach_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '考试白哦' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for fees
-- ----------------------------
DROP TABLE IF EXISTS `fees`;
CREATE TABLE `fees`  (
  `fee_id` int(11) NOT NULL AUTO_INCREMENT,
  `fee_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `amount` decimal(10, 2) NOT NULL,
  `is_installment` tinyint(1) NULL DEFAULT 0,
  `installment_count` int(11) NULL DEFAULT 0,
  `payment_options` json NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`fee_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for leave_requests
-- ----------------------------
DROP TABLE IF EXISTS `leave_requests`;
CREATE TABLE `leave_requests`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '请假记录ID',
  `coach_id` int(11) NOT NULL COMMENT '教练ID',
  `reason` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请假原因',
  `start_date` date NOT NULL COMMENT '请假开始日期',
  `end_date` date NOT NULL COMMENT '请假结束日期',
  `status` enum('待审核','已批准','已拒绝') CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '待审核' COMMENT '请假状态',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间',
  `admin_id` int(11) NULL DEFAULT NULL,
  `reviewed_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `review_comments` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `evidence_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `coach_id`(`coach_id`) USING BTREE,
  CONSTRAINT `leave_requests_ibfk_1` FOREIGN KEY (`coach_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '请假记录表，用于记录教练请假信息\r\n表设计人-软件22-7王赟昊' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for password_resets
-- ----------------------------
DROP TABLE IF EXISTS `password_resets`;
CREATE TABLE `password_resets`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识，主键，用于标识数据行',
  `user_id` int(11) NOT NULL COMMENT '用户id，标识这条重置链接重置的用户',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '重置链接后随机生成的校验',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '重置链接创建时间，用于确定链接的过期时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `token`(`token`) USING BTREE COMMENT '加速查找tocken，确保tocken不一致，使用BTREE实现唯一索引',
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '表设计人软件22-7王赟昊\r\n用于密码遗忘重置的tocken存储' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for payment_records
-- ----------------------------
DROP TABLE IF EXISTS `payment_records`;
CREATE TABLE `payment_records`  (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `fee_id` int(11) NOT NULL,
  `amount` decimal(10, 2) NOT NULL,
  `payment_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `payment_method` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `payment_status` enum('成功','失败') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`payment_id`) USING BTREE,
  INDEX `fee_id`(`fee_id`) USING BTREE,
  CONSTRAINT `payment_records_ibfk_1` FOREIGN KEY (`fee_id`) REFERENCES `fees` (`fee_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for refunds
-- ----------------------------
DROP TABLE IF EXISTS `refunds`;
CREATE TABLE `refunds`  (
  `refund_id` int(11) NOT NULL AUTO_INCREMENT,
  `payment_id` int(11) NOT NULL,
  `refund_amount` decimal(10, 2) NOT NULL,
  `refund_reason` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `refund_status` enum('处理中','已完成') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `refund_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`refund_id`) USING BTREE,
  INDEX `payment_id`(`payment_id`) USING BTREE,
  CONSTRAINT `refunds_ibfk_1` FOREIGN KEY (`payment_id`) REFERENCES `payment_records` (`payment_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for schedule
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coach_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `capacity` int(11) NULL DEFAULT 4,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `coach_id`(`coach_id`) USING BTREE,
  CONSTRAINT `schedule_ibfk_1` FOREIGN KEY (`coach_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for student_sorce
-- ----------------------------
DROP TABLE IF EXISTS `student_sorce`;
CREATE TABLE `student_sorce`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `test_name` enum('科目一','科目二','科目三','科目四') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '科目一',
  `grade` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for student_state
-- ----------------------------
DROP TABLE IF EXISTS `student_state`;
CREATE TABLE `student_state`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '唯一标识',
  `student_id` int(11) NOT NULL COMMENT '标记学员的id',
  `state` enum('新学员','科目一','科目二','科目三','科目四','拿证') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '新学员' COMMENT '标记学员的学习状况',
  `study_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标记学员的考什么证',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `student_id`(`student_id`) USING BTREE,
  CONSTRAINT `student_id` FOREIGN KEY (`student_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用于记录学员的报名情况，和考试通过情况表\r\n表设计人软件22-7王赟昊' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students`  (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `registration_status` enum('PENDING','APPROVED','REJECTED') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'PENDING',
  `reject_reason` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `review_time` timestamp NULL DEFAULT NULL,
  `reviewer_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`student_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for teach_info
-- ----------------------------
DROP TABLE IF EXISTS `teach_info`;
CREATE TABLE `teach_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `coach_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `teach_level` enum('科目二','科目三') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '科目二',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `coach_id`(`coach_id`) USING BTREE,
  INDEX `student_id`(`student_id`) USING BTREE,
  CONSTRAINT `teach_info_ibfk_1` FOREIGN KEY (`coach_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `teach_info_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `users` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id，用于唯一表名用户id',
  `role` enum('user','coach','admin') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'user' COMMENT '用户角色，枚举类型，user学员coach教练admin管理员',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '请补全个人信息' COMMENT '实名认证信息，本人真名',
  `idnumber` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '请补全个人信息' COMMENT '实名认证信息，身份证号',
  `phonenumber` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱地址，绑定账号，用于找回',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码，加密存储',
  `finish_info` enum('yes','no') CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'no' COMMENT '是否完成实名认证标识',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建账户时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `phonenumber`(`phonenumber`) USING BTREE COMMENT '用于确保没有两个人电话一样，顺便加速查询速度，使用BTREE实现唯一索引',
  UNIQUE INDEX `email`(`email`) USING BTREE COMMENT '用于确保没有两个人邮箱一样，顺便加速查询速度，使用BTREE实现唯一索引',
  UNIQUE INDEX `idnumber`(`idnumber`) USING BTREE COMMENT '用于确保没有两个人身份证号一样，顺便加速查询速度，使用BTREE实现唯一索引'
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设计者22-7 王赟昊，项目全部用户储存所在的表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
