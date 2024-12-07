<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2024/12/7
  Time: 13:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>课程管理系统</title>
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
<h1>课程管理系统</h1>
<div class="container">
    <h2>创建新课程</h2>
    <form id="course-form">
        <label for="course-number">课程编号:</label>
        <input type="text" id="course-number" name="course_number" required>

        <label for="course-name">课程名称:</label>
        <input type="text" id="course-name" name="name" required>

        <label for="start-time">选课开始时间:</label>
        <input type="datetime-local" id="start-time" name="start_time" required>
        <br>
        <label for="end-time">选课结束时间:</label>
        <input type="datetime-local" id="end-time" name="end_time" required>

        <label for="capacity">可容纳人数:</label>
        <input type="number" id="capacity" name="capacity" required>

        <label for="content">教学内容:</label>
        <textarea id="content" name="content" rows="4" required></textarea>

        <button type="button" onclick="submitCourse()">创建课程</button>
    </form>

    <h2>我的课程</h2>
    <table id="course-table">
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
</div>

<script src="js/coach_lesson.js"></script>

</body>
</html>
