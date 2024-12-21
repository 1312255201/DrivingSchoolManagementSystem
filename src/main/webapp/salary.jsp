<%--
Created by IntelliJ IDEA.
User: AFish
Date: 2024/12/21
Time: 20:12
To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>发放工资</title>
  <style>
    h1 {
      text-align: center;
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
    button {
      padding: 8px 12px;
      background-color: #007BFF;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }
    button:hover {
      background-color: #0056b3;
    }
    .cancel-button {
      background-color: #FF6F61;
    }
    .cancel-button:hover {
      background-color: #FF4C39;
    }
    .total-salary {
      font-weight: bold;
      color: green;
    }
  </style>
</head>
<body>
<h1>发放工资</h1>
<!-- 教练信息表格 -->
<table>
  <thead>
  <tr>
    <th>ID</th>
    <th>教练姓名</th>
    <th>银行卡号</th>
    <th>基础工资</th>
    <th>奖金</th>
    <th>总金额</th>
    <th>操作</th>
  </tr>
  </thead>
  <tbody id="coaches-tbody"></tbody>
</table>

<!-- 工资发放记录表格 -->
<h2>所有工资发放记录</h2>
<table>
  <thead>
  <tr>
    <th>教练姓名</th>
    <th>工资金额</th>
    <th>发放时间</th>
    <th>备注</th>
  </tr>
  </thead>
  <tbody id="salary-records-tbody"></tbody>
</table>

<script src="js/salary.js">

</script>

</body>
</html>
