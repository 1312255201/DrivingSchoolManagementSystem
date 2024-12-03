<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2024/12/3
  Time: 19:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>驾校管理系统 - 注册</title>
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

        .register-container {
            width: 100%;
            max-width: 400px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }

        .register-container h1 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }

        .register-container label {
            font-weight: bold;
            color: #555;
            display: block;
            margin-bottom: 5px;
        }

        .register-container input[type="text"],
        .register-container input[type="password"],
        .register-container input[type="email"] {
            width: 95%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .register-container button {
            width: 100%;
            padding: 10px;
            background-color: #28a745;
            border: none;
            color: white;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .register-container button:hover {
            background-color: #218838;
        }

        .register-container .footer {
            text-align: center;
            margin-top: 10px;
            font-size: 14px;
            color: #666;
        }

        .register-container .footer a {
            color: #007BFF;
            text-decoration: none;
        }

        .register-container .footer a:hover {
            text-decoration: underline;
        }

        .alert {
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 5px;
            font-size: 14px;
            text-align: center;
        }

        .alert-error {
            background-color: #f8d7da;
            color: #842029;
            border: 1px solid #f5c2c7;
        }

        .alert-success {
            background-color: #d1e7dd;
            color: #0f5132;
            border: 1px solid #badbcc;
        }
    </style>
</head>
<body>
<div class="register-container">
    <h1>美林驾校</h1>

    <!-- 显示消息提示 -->
    <%
        String error = request.getParameter("error");
        String success = request.getParameter("success");

        if (error != null) {
            String errorMessage;
            switch (error) {
                case "emptyFields":
                    errorMessage = "请填写所有字段！";
                    break;
                case "passwordMismatch":
                    errorMessage = "两次输入的密码不一致！";
                    break;
                case "registrationFailed":
                    errorMessage = "注册失败，请稍后再试！";
                    break;
                case "databaseError":
                    errorMessage = "你的手机号或者邮箱已经被注册了！";
                    break;
                default:
                    errorMessage = "发生未知错误！";
                    break;
            }
    %>
    <div class="alert alert-error"><%= errorMessage %></div>
    <%
    } else if (success != null) {
    %>
    <div class="alert alert-success">注册成功！请前往 <a href="login.jsp">登录</a>。</div>
    <%
        }
    %>

    <form action="RegisterServlet" method="post">
        <label for="username">手机号</label>
        <input type="text" id="username" name="username" placeholder="请输入手机号" required>

        <label for="email">邮箱</label>
        <input type="email" id="email" name="email" placeholder="请输入邮箱地址" required>

        <label for="password">密码</label>
        <input type="password" id="password" name="password" placeholder="请输入密码" required>

        <label for="confirmPassword">确认密码</label>
        <input type="password" id="confirmPassword" name="confirmPassword" placeholder="请再次输入密码" required>

        <button type="submit">注册</button>
    </form>
    <div class="footer">
        <p>已有账号？<a href="login.jsp">立即登录</a></p>
    </div>
</div>
</body>
</html>
