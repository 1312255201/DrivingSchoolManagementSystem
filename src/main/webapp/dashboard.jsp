<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2024/12/3
  Time: 22:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>驾校管理系统 - 控制台</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-color: #f3f4f6;
            color: #333;
        }

        .dashboard {
            display: flex;
            height: 100vh;
        }

        .sidebar {
            width: 250px;
            background-color: #007BFF;
            color: white;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            padding: 10px;
            box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
        }

        .sidebar h2 {
            text-align: center;
            margin-bottom: 20px;
            font-size: 22px;
        }

        .sidebar a {
            display: block; /* 每个导航项独占一行 */
            padding: 15px 20px;
            margin: 5px 0;
            text-decoration: none;
            color: white;
            background-color: #0056b3;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .sidebar a:hover {
            background-color: #004085;
        }

        .sidebar a.active {
            background-color: #003566;
        }

        .logout {
            display: block;
            padding: 15px 20px;
            text-decoration: none;
            color: white;
            background-color: #DC3545;
            text-align: center;
            border-radius: 5px;
            transition: background-color 0.3s;
        }

        .logout:hover {
            background-color: #a71d2a;
        }

        .content {
            flex-grow: 1;
            padding: 20px;
            background-color: #fff;
        }
    </style>
</head>
<body>
<div class="dashboard">
    <!-- 左侧导航栏 -->
    <div class="sidebar">
        <div>
            <h2>驾校管理系统</h2>
            <%
                session = request.getSession(false);
                String userRole = (String) session.getAttribute("userrole"); // 获取用户身份

                if ("admin".equals(userRole)) {
            %>
            <a href="dashboard.jsp?section=home" class="<%= "home".equals(request.getParameter("section")) || request.getParameter("section") == null ? "active" : "" %>">概览</a>
            <a href="dashboard.jsp?section=profile" class="<%= "home".equals(request.getParameter("section")) || request.getParameter("section") == null ? "active" : "" %>">个人资料</a>
            <a href="dashboard.jsp?section=students" class="<%= "students".equals(request.getParameter("section")) ? "active" : "" %>">学员管理</a>
            <a href="dashboard.jsp?section=instructors" class="<%= "instructors".equals(request.getParameter("section")) ? "active" : "" %>">教练管理</a>
            <a href="dashboard.jsp?section=settings" class="<%= "settings".equals(request.getParameter("section")) ? "active" : "" %>">系统设置</a>
            <%
            } else if ("user".equals(userRole)) {
            %>
            <a href="dashboard.jsp?section=home" class="<%= "home".equals(request.getParameter("section")) || request.getParameter("section") == null ? "active" : "" %>">我的课程</a>
            <a href="dashboard.jsp?section=profile" class="<%= "home".equals(request.getParameter("section")) || request.getParameter("section") == null ? "active" : "" %>">个人资料</a>
            <a href="dashboard.jsp?section=schedule" class="<%= "schedule".equals(request.getParameter("section")) ? "active" : "" %>">我的日程</a>
            <%
            } else if ("coach".equals(userRole)) {
            %>
            <a href="dashboard.jsp?section=home" class="<%= "home".equals(request.getParameter("section")) || request.getParameter("section") == null ? "active" : "" %>">我的学员</a>
            <a href="dashboard.jsp?section=profile" class="<%= "home".equals(request.getParameter("section")) || request.getParameter("section") == null ? "active" : "" %>">个人资料</a>
            <a href="dashboard.jsp?section=schedule" class="<%= "schedule".equals(request.getParameter("section")) ? "active" : "" %>">我的课程表</a>
            <%
                } else {
                    response.sendRedirect("login.jsp"); // 未登录或角色不明确时跳转到登录页面
                }
            %>
        </div>
        <!-- 退出登录 -->
        <a href="LogoutServlet" class="logout">退出登录</a>
    </div>
    <!-- 右侧内容区域 -->
    <div class="content">
        <%
            String section = request.getParameter("section");
            if ("students".equals(section)) {
        %><jsp:include page="students.jsp" /> <%
    } else if ("instructors".equals(section)) {
    %><jsp:include page="instructors.jsp" /> <%
    } else if ("settings".equals(section)) {
    %><jsp:include page="settings.jsp" /> <%
    }else if ("profile".equals(section)) {
    %><jsp:include page="profile.jsp" /> <%
    }else {
    %><jsp:include page="home.jsp" /> <%
        }
    %>
    </div>
</div>
</body>
</html>
