<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2024/12/3
  Time: 23:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>修改个人信息</title>
    <style>
        .form-container {
            max-width: 800px;
            margin: 0 auto;
            background: #ffffff;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }

        h1 {
            text-align: center;
            color: #333;
        }

        .profile-pic {
            text-align: center;
            margin-bottom: 20px;
        }

        .profile-pic img {
            max-width: 150px;
            border-radius: 50%;
            border: 2px solid #ccc;
        }

        .info-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 20px;
        }

        .info-item {
            background: #f9f9f9;
            padding: 15px;
            border-radius: 6px;
            box-shadow: 0 1px 5px rgba(0, 0, 0, 0.1);
        }

        .info-item label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .info-item span {
            display: block;
            margin-bottom: 10px;
            color: #555;
        }

        .info-item button {
            padding: 8px 16px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }

        .info-item button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h1>个人信息</h1>
    <!-- 个人证件照 -->
    <div class="profile-pic">
        <% String avatarUrl = (String) request.getSession(false).getAttribute("avatar"); %>
        <% if (avatarUrl != null && !avatarUrl.isEmpty()) { %>
        <img src="<%= avatarUrl %>" alt="证件照">
        <% } else { %>
        <img src="default-avatar.png" alt="默认头像">
        <% } %>
    </div>

    <!-- 信息展示 -->
    <div class="info-grid">
        <div class="info-item">
            <label>姓名</label>
            <span><%= request.getSession(false).getAttribute("username") %></span>
            <button onclick="window.location.href='EditProfileServlet?field=name'">修改</button>
        </div>
        <div class="info-item">
            <label>手机号</label>
            <span><%= request.getSession(false).getAttribute("userphonenumber") %></span>
            <button onclick="window.location.href='EditProfileServlet?field=phonenumber'">修改</button>
        </div>
        <div class="info-item">
            <label>邮箱</label>
            <span><%= request.getSession(false).getAttribute("useremail") %></span>
            <button onclick="window.location.href='EditProfileServlet?field=email'">修改</button>
        </div>
        <div class="info-item">
            <label>其他信息</label>
            <span>这里可以展示其他字段</span>
            <button onclick="window.location.href='EditProfileServlet?field=other'">修改</button>
        </div>
    </div>
</div>
</body>
</html>
