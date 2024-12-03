<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2024/12/3
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>驾校管理系统 - 登录</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f3f4f6;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .login-container {
            width: 100%;
            max-width: 400px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }

        .login-container h1 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }

        .login-container label {
            font-weight: bold;
            color: #555;
            display: block;
            margin-bottom: 5px;
        }

        .login-container input[type="text"],
        .login-container input[type="password"] {
            width: 95%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .login-container button {
            width: 100%;
            padding: 10px;
            background-color: #007BFF;
            border: none;
            color: white;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .login-container button:hover {
            background-color: #0056b3;
        }

        .login-container .footer {
            text-align: center;
            margin-top: 10px;
            font-size: 14px;
            color: #666;
        }

        .login-container .footer a {
            color: #007BFF;
            text-decoration: none;
        }

        .login-container .footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h1>驾校管理系统</h1>

    <!-- 显示错误消息 -->
    <%
        String error = request.getParameter("error");

        if (error != null) {
            String errorMessage;
            switch (error) {
                case "emptyFields":
                    errorMessage = "手机号或密码不能为空！";
                    break;
                case "invalidCredentials":
                    errorMessage = "手机号或密码错误！";
                    break;
                case "databaseError":
                    errorMessage = "数据库错误，请联系管理员！";
                    break;
                default:
                    errorMessage = "发生未知错误！";
                    break;
            }
    %>
    <div class="alert alert-error"><%= errorMessage %></div>
    <%
        }
    %>

    <form action="LoginServlet" method="post">
        <label for="username">手机号</label>
        <input type="text" id="username" name="username" placeholder="请输入手机号" required>

        <label for="password">密码</label>
        <input type="password" id="password" name="password" placeholder="请输入密码" required>

        <button type="submit">登录</button>
    </form>
    <div class="footer">
        <p>没有账号？<a href="register.jsp">立即注册</a></p>
    </div>
</div>
</body>
</html>

