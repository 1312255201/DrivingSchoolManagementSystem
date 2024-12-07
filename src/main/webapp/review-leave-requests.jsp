<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2024/12/8
  Time: 01:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>审核请假申请</title>
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
    .approve-btn, .reject-btn {
      padding: 5px 10px;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      color: white;
    }
    .approve-btn {
      background-color: #28a745;
    }
    .reject-btn {
      background-color: #dc3545;
    }
    .approve-btn:hover {
      background-color: #218838;
    }
    .reject-btn:hover {
      background-color: #c82333;
    }
  </style>
  <script src="js/review-leave-requests.js"></script>

</head>
<body>
<h1>审核请假申请</h1>
<table>
  <thead>
  <tr>
    <th>ID</th>
    <th>教练姓名</th>
    <th>请假原因</th>
    <th>开始日期</th>
    <th>结束日期</th>
    <th>状态</th>
    <th>提交时间</th>
    <th>操作</th>
  </tr>
  </thead>
  <tbody id="leave-requests-tbody"></tbody>
</table>
</body>
</html>