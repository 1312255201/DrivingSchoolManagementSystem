<%--
  Created by IntelliJ IDEA.
  User: Gky
  Date: 2024/12/9
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>考试管理系统</title>
    <style>
        h1 {
            text-align: center;
            color: #333;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #4caf50;
            color: white;
        }
        button {
            padding: 8px 12px;
            background-color: #ff4d4f;
            color: #fff;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }
        button:hover {
            opacity: 0.8;
        }
    </style>
</head>
<body>
<h1>考试管理系统</h1>
<div class="container">
    <h2>创建新考试</h2>
    <form id="exam-form">
        <label for="exam-number">考试编号:</label>
        <input type="text" id="exam-number" name="exam_number" required>

        <label for="exam-name">考试名称:</label>
        <input type="text" id="exam-name" name="name" required>
        <br>
        <label for="start-time">考试开始时间:</label>
        <input type="datetime-local" id="start-time" name="start_time" required>
        <label for="end-time">考试结束时间:</label>
        <input type="datetime-local" id="end-time" name="end_time" required>
        <br>
        <label for="capacity">可容纳人数:</label>
        <input type="number" id="capacity" name="capacity" required>

        <label for="content">考试内容:</label>
        <textarea id="content" name="content" rows="4" required></textarea>

        <button type="button" onclick="submitexam()">创建考试</button>
    </form>

</div>
<table id="exam-table">
    <thead>
    <tr>
        <th>课程编号</th>
        <th>课程名称</th>
        <th>选课时间</th>
        <th>容量</th>
        <th>教学内容</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <!-- 动态加载课程数据 -->
    </tbody>
</table>
<script src="js/admin_exam.js"></script>

</body>
</html>