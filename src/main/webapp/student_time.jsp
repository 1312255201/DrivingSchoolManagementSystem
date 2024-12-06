<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2024/12/7
  Time: 01:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>学员练车预约</title>
    <style>

        .container {
            max-width: 800px;
            margin: 0 auto;
        }
        h1, h2 {
            text-align: center;
        }
        .schedule-table, .appointment-table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        .schedule-table th, .schedule-table td,
        .appointment-table th, .appointment-table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }
        .schedule-table th, .appointment-table th {
            background-color: #f4f4f4;
        }
        .btn {
            padding: 5px 10px;
            font-size: 14px;
            cursor: pointer;
            border: none;
            border-radius: 5px;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
        }
        .btn-danger {
            background-color: #dc3545;
            color: white;
        }
        .btn-primary:hover, .btn-danger:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>学员练车预约系统</h1>

    <!-- 当前预约情况 -->
    <h2>我的预约</h2>
    <table class="appointment-table">
        <thead>
        <tr>
            <th>预约日期</th>
            <th>时间段</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="appointment-list">
        <!-- 动态加载 -->
        </tbody>
    </table>

    <!-- 可预约时间段 -->
    <h2>可预约时间段</h2>
    <table class="schedule-table">
        <thead>
        <tr>
            <th>日期</th>
            <th>时间段</th>
            <th>剩余名额</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="schedule-list">
        <!-- 动态加载 -->
        </tbody>
    </table>
</div>

<script src="js/student_time.js"></script>

</body>
</html>
