<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2024/12/3
  Time: 23:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="java.io.File" %>
<%@ page import="java.nio.file.Files" %>
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

        /* 模态框样式 */
        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            justify-content: center;
            align-items: center;
        }

        .modal-content {
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            width: 400px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
            text-align: center;
        }

        .modal-content h2 {
            margin-bottom: 20px;
        }

        .modal-content input[type="text"] {
            width: 90%;
            padding: 8px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        .modal-content .form-actions button {
            padding: 10px 20px;
            margin: 10px 5px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .modal-content .form-actions .cancel-btn {
            background-color: #dc3545;
            color: white;
        }

        .modal-content .form-actions .cancel-btn:hover {
            background-color: #a71d2a;
        }

        .modal-content .form-actions button {
            background-color: #007BFF;
            color: white;
        }

        .modal-content .form-actions button:hover {
            background-color: #0056b3;
        }

        .close {
            position: absolute;
            top: 10px;
            right: 20px;
            font-size: 20px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="form-container">
    <h1>个人信息</h1>
    <!-- 个人证件照 -->
    <div class="profile-pic">
        <% String avatarUrl = (String) request.getSession(false).getAttribute("avatar"); %>
        <% if (avatarUrl != null && !avatarUrl.isEmpty() ) { %>
        <img src="<%= avatarUrl %>" alt="证件照">
        <% } else { %>
        <img src="img/default-avatar.png" alt="默认头像">
        <% } %>
    </div>

    <!-- 信息展示 -->
    <div class="info-grid">
        <div class="info-item">
            <label>姓名</label>
            <span id="display-name"><%= request.getSession(false).getAttribute("username") %></span>
            <button onclick="openModal('name', '<%= request.getSession(false).getAttribute("username") %>')">修改</button>
        </div>
        <div class="info-item">
            <label>手机号</label>
            <span id="display-phonenumber"><%= request.getSession(false).getAttribute("userphonenumber") %></span>
            <button onclick="openModal('phonenumber', '<%= request.getSession(false).getAttribute("userphonenumber") %>')">修改</button>
        </div>
        <div class="info-item">
            <label>邮箱</label>
            <span id="display-email"><%= request.getSession(false).getAttribute("useremail") %></span>
            <button onclick="openModal('email', '<%= request.getSession(false).getAttribute("useremail") %>')">修改</button>
        </div>
        <div class="info-item">
            <label>身份证号</label>
            <span id="display-idnumber"><%= request.getSession(false).getAttribute("useridnumber") %></span>
            <button onclick="openModal('idnumber', '<%= request.getSession(false).getAttribute("useridnumber") %>')">修改</button>
        </div>
        <div class="info-item">
            <label>密码</label>
            <span id="display-password">*******</span>
            <button onclick="openModal('password', '*******')">修改</button>
        </div>
        <div class="info-item">
            <label>权限组</label>
            <span id="display-role"><%= request.getSession(false).getAttribute("userrole") %></span>
        </div>
    </div>
</div>

<!-- 模态框 -->
<div id="editModal" class="modal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h2 id="modalTitle">编辑字段</h2>
        <form id="editForm">
            <input type="hidden" id="field" name="field">
            <label for="newValue">新值:</label>
            <input type="text" id="newValue" name="newValue" required>
            <div class="form-actions">
                <button type="button" onclick="submitEdit()">保存修改</button>
                <button type="button" class="cancel-btn" onclick="closeModal()">取消</button>
            </div>
        </form>
    </div>
</div>
<script src="js/profile.js"></script>

</body>
</html>
