<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>课程管理</title>
    <link rel="stylesheet" type="text/css" href="css/usermanagement.css">

</head>
<body>
<h1>课程管理</h1>

<div class="filter-bar">
    <input type="text" id="searchInput" placeholder="输入课程号和课程名" onkeyup="filterTable1()">

</div>

<table id="userTable">
    <thead>
    <tr>
        <th>id</th>
        <th>课程号</th>
        <th>课程名</th>
        <th>开始时间</th>
        <th>结束时间</th>
        <th>剩余名额</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <%
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtil.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM courses");

            while (rs.next()) {
                int id = rs.getInt("id");
                String course_number = rs.getString("course_number");
                String name = rs.getString("name");
                Timestamp start_time = rs.getTimestamp("start_time");
                Timestamp end_time = rs.getTimestamp("end_time");
                String capacity = rs.getString("capacity");
    %>
    <tr>
        <td><%= id %></td>
        <td><%= course_number %></td>
        <td><%= name %></td>
        <td><%= start_time %></td>
        <td><%= end_time %></td>
        <td><%= capacity %></td>
        <td>
            <button  onclick="openEditModal(<%= id %>)">选课</button>
        </td>
    </tr>
    <%
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }
    %>
    </tbody>
</table>

<script src="js/select_lesson.js"></script>

</body>
</html>
