<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2024/12/7
  Time: 01:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>教练预约管理</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 900px;
            margin: 20px auto;
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            color: #333;
        }
        .form-section, .list-section {
            margin-bottom: 20px;
        }
        .form-section label {
            font-size: 16px;
            margin-right: 10px;
        }
        .form-section select, .form-section button {
            padding: 10px;
            font-size: 14px;
        }
        .btn {
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #0056b3;
        }
        .schedule-list, .appointment-list {
            border: 1px solid #ddd;
            padding: 15px;
            border-radius: 8px;
            background: #f9f9f9;
        }
        .schedule-item, .appointment-item {
            border-bottom: 1px solid #ddd;
            padding: 10px 0;
        }
        .schedule-item:last-child, .appointment-item:last-child {
            border-bottom: none;
        }
        .empty-message {
            text-align: center;
            color: #888;
            font-size: 16px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>教练预约管理</h1>

    <!-- 设置可预约时间段 -->
    <div class="form-section">
        <h2>设置可预约时间段</h2>
        <label for="date">选择日期：</label>
        <input type="date" id="date" required>
        <label for="time">选择时间段：</label>
        <select id="time">
            <option value="08:00-10:00">08:00-10:00</option>
            <option value="10:00-12:00">10:00-12:00</option>
            <option value="14:00-16:00">14:00-16:00</option>
            <option value="16:00-18:00">16:00-18:00</option>
        </select>
        <button class="btn" onclick="addSchedule()">添加时间段</button>
    </div>

    <!-- 查看可预约时间段 -->
    <div class="list-section">
        <h2>已设置时间段</h2>
        <div class="schedule-list" id="schedule-list">
            <p class="empty-message">暂无已设置的时间段。</p>
        </div>
    </div>

    <!-- 查看学员预约情况 -->
    <div class="list-section">
        <h2>学员预约情况</h2>
        <div class="appointment-list" id="appointment-list">
            <p class="empty-message">暂无学员预约。</p>
        </div>
    </div>
</div>
<script src="js/coach-time.js"></script>

</body>
</html>
