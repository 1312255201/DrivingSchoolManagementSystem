<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2024/12/6
  Time: 23:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.sql.*" %>
<%@ page import="cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>考证进度</title>
    <style>
        h1 {
            text-align: center;
        }
        .progress-container {
            margin: 20px auto;
            max-width: 800px;
        }
        .status {
            padding: 10px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        .subject-section {
            margin-bottom: 30px;
        }
        .subject-header {
            background-color: #f0f8ff;
            padding: 10px;
            border-radius: 8px;
            border: 1px solid #ccc;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
        }
        table th, table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        table th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<h1>考证进度</h1>
<div class="progress-container">
    <%
        // 获取当前登录学员 ID
        session = request.getSession(false);
        Integer studentId = (Integer) session.getAttribute("userid");
        boolean isEnrolled = false;
        String studyType = "";
        String currentState = "";

        try (Connection conn = DBUtil.getConnection()) {
            // 检查学员是否在 student_state 表中
            String stateQuery = "SELECT state, study_type FROM student_state WHERE student_id = ?";
            PreparedStatement stateStmt = conn.prepareStatement(stateQuery);
            stateStmt.setInt(1, studentId);
            ResultSet stateRs = stateStmt.executeQuery();

            if (stateRs.next()) {
                isEnrolled = true;
                currentState = stateRs.getString("state");
                studyType = stateRs.getString("study_type");
            }
    %>

    <div class="status">
        <% if (isEnrolled) { %>
        <p>报考类型: <strong><%= studyType %></strong></p>
        <p>当前状态: <strong><%= currentState %></strong></p>
        <% } else { %>
        <p>您尚未报考。</p>
        <% } %>
    </div>

    <% if (isEnrolled) { %>
    <%-- 遍历科目 --%>
    <% String[] subjects = {"科目一", "科目二", "科目三", "科目四"}; %>
    <% for (String subject : subjects) { %>
    <div class="subject-section">
        <div class="subject-header">
            <h3><%= subject + "进度" %></h3>
        </div>
        <%
            // 获取学员的成绩
            String scoreQuery = "SELECT grade FROM student_sorce WHERE student_id = ? AND test_name = ?";
            PreparedStatement scoreStmt = conn.prepareStatement(scoreQuery);
            scoreStmt.setInt(1, studentId);
            scoreStmt.setString(2, subject);
            ResultSet scoreRs = scoreStmt.executeQuery();

            boolean hasScores = false;
        %>
        <table>
            <thead>
            <tr>
                <th>考试次数</th>
                <th>成绩</th>
            </tr>
            </thead>
            <%
                int tmpid = 1; // 定义自增变量并初始化为 1
            %>
            <tbody>
            <% while (scoreRs.next()) { %>
            <% hasScores = true; %>
            <tr>
                <td><%= tmpid++ %></td> <!-- 在这里输出并自增 -->
                <td><%= scoreRs.getInt("grade") %></td>
            </tr>
            <% } %>
            <% if (!hasScores) { %>
            <tr>
                <td colspan="2">暂无考试成绩</td>
            </tr>
            <% } %>
            </tbody>

        </table>

        <% if (subject.equals("科目二") || subject.equals("科目三")) { %>
        <%-- 获取教练信息 --%>
        <%
            String coachQuery = "SELECT u.name, u.phonenumber FROM teach_info sc " +
                    "JOIN users u ON sc.coach_id = u.id " +
                    "WHERE sc.student_id = ? AND sc.teach_level = ?";
            PreparedStatement coachStmt = conn.prepareStatement(coachQuery);
            coachStmt.setInt(1, studentId);
            coachStmt.setString(2, subject);
            System.out.println(coachStmt);
            ResultSet coachRs = coachStmt.executeQuery();

            if (coachRs.next()) {
        %>
        <p>教练信息:</p>
        <ul>
            <li>姓名: <%= coachRs.getString("name") %></li>
            <li>电话: <%= coachRs.getString("phonenumber") %></li>
        </ul>
        <% } else { %>
        <p>暂无教练信息</p>
        <% } %>
        <% } %>
    </div>
    <% } %>
    <% } %>
    <%
        } catch (Exception e) {
            e.printStackTrace();
        }
    %>
</div>
</body>
</html>
