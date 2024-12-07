<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2024/12/7
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>学生选课系统</title>
    <style>
        h1 { text-align: center; }
        table { width: 100%; border-collapse: collapse; margin: 20px 0; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f4f4f4; }
        .btn { padding: 5px 10px; color: #fff; border: none; cursor: pointer; border-radius: 4px; }
        .btn-select { background-color: #28a745; }
        .btn-cancel { background-color: #dc3545; }
        .btn:hover { opacity: 0.8; }
    </style>
</head>
<body>
<h1>学生选课系统</h1>

<h3>可选课程</h3>
<table id="available-courses">
    <thead>
    <tr>
        <th>课程号</th>
        <th>课程名称</th>
        <th>开始时间</th>
        <th>结束时间</th>
        <th>剩余名额</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <tr><td colspan="6">加载中...</td></tr>
    </tbody>
</table>

<h3>已选课程</h3>
<table id="selected-courses">
    <thead>
    <tr>
        <th>课程号</th>
        <th>课程名称</th>
        <th>开始时间</th>
        <th>结束时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <tr><td colspan="5">加载中...</td></tr>
    </tbody>
</table>

<script src="js/student_select_lessen.js"></script>

</body>
</html>
