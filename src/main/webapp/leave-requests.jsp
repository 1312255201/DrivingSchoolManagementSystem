<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2024/12/8
  Time: 00:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>请假管理</title>
  <style>
    h1 {
      color: #333;
    }
    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 20px;
    }
    th, td {
      border: 1px solid #ddd;
      padding: 10px;
      text-align: left;
    }
    th {
      background-color: #007BFF;
      color: white;
    }
    .submit-btn {
      background-color: #28a745;
      color: white;
      border: none;
      padding: 10px 15px;
      border-radius: 5px;
      cursor: pointer;
    }
    .submit-btn:hover {
      background-color: #218838;
    }
    .form-container {
      margin-top: 20px;
      padding: 15px;
      background: #fff;
      border-radius: 8px;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    }
    label {
      display: block;
      margin-top: 10px;
    }
    input, textarea {
      width: 100%;
      padding: 8px;
      margin-top: 5px;
    }
  </style>
  <script src="js/leave-requests.js"></script>

</head>
<body>
<h1>请假管理</h1>

<div class="form-container">
  <h2>提交请假申请</h2>
  <label for="reason">请假原因:</label>
  <textarea id="reason" rows="4"></textarea>

  <label for="start-date">开始时间:</label>
  <input type="date" id="start-date">

  <label for="end-date">结束时间:</label>
  <input type="date" id="end-date">

  <label for="evidence">佐证文件:</label>
  <input type="file" id="evidence">

  <button class="submit-btn" onclick="submitLeaveRequest()">提交申请</button>
</div>


<h2>我的请假记录</h2>
<table>
  <thead>
  <tr>
    <th>ID</th>
    <th>请假原因</th>
    <th>开始日期</th>
    <th>结束日期</th>
    <th>状态</th>
    <th>提交时间</th>
  </tr>
  </thead>
  <tbody id="leave-requests-tbody"></tbody>
</table>
</body>
</html>
