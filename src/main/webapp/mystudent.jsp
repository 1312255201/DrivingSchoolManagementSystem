<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2024/12/7
  Time: 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>我的学员</title>
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
        .filter-group {
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        select, button {
            padding: 10px;
            font-size: 14px;
            margin-left: 10px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        .passed {
            color: green;
            font-weight: bold;
        }
        .in-progress {
            color: orange;
            font-weight: bold;
        }
    </style>
</head>
<body>
<h1>我的学员</h1>
<div class="container">
    <div class="filter-group">
        <label for="filter">筛选学员：</label>
        <select id="filter" onchange="loadStudents()">
            <option value="all">全部学员</option>
            <option value="in-progress">正在学习的学员</option>
            <option value="passed">已通过的学员</option>
        </select>
    </div>
    <table>
        <thead>
        <tr>
            <th>学员姓名</th>
            <th>手机号</th>
            <th>学科</th>
            <th>状态</th>
            <th>备注</th>
        </tr>
        </thead>
        <tbody id="student-table-body">
        <!-- 动态生成内容 -->
        </tbody>
    </table>
</div>

<script src="js/mystudent.js"></script>

</body>
</html>
