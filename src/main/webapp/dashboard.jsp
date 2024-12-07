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
            display: flex;
            min-height: 100vh; /* 使页面高度至少等于视口高度 */
            overflow: hidden; /* 禁止 body 出现滚动条 */
        }

        .dashboard {
            display: flex;
            flex: 1; /* 使 dashboard 占满视口 */
            overflow: hidden; /* 避免内部滚动条干扰 */
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
            position: sticky; /* 固定 sidebar */
            top: 0; /* 从顶部开始固定 */
            height: 100vh; /* 高度占满整个视口 */
            overflow-y: auto; /* 若内容过多，则 sidebar 内部可以滚动 */
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
            flex-grow: 1; /* 占用剩余空间 */
            padding: 20px;
            background-color: #fff;
            overflow-y: auto; /* 启用垂直滚动 */
            height: 100vh; /* 高度等于视口 */
            box-sizing: border-box; /* 包含 padding */

        }
    </style>
</head>
<body>
<div class="dashboard">
    <!-- 左侧导航栏 -->
    <div class="sidebar">
        <div>
            <h2>感谢你选择美林驾校</h2>
            <%
                session = request.getSession(false);
                String userRole = (String) session.getAttribute("userrole"); // 获取用户身份

                if ("admin".equals(userRole)) {
            %>
            <a href="dashboard.jsp?section=home" class="<%= "home".equals(request.getParameter("section")) || request.getParameter("section") == null ? "active" : "" %>">概览</a>
            <a href="dashboard.jsp?section=profile" class="<%= "home".equals(request.getParameter("section")) || request.getParameter("section") == null ? "active" : "" %>">个人资料</a>
            <a href="dashboard.jsp?section=usermanage" class="<%= "usermanage".equals(request.getParameter("section")) ? "active" : "" %>">用户管理</a>
            <a href="dashboard.jsp?section=assignstudent" class="<%= "assignstudent".equals(request.getParameter("section")) ? "active" : "" %>">学员分配</a>
            <a href="dashboard.jsp?section=review-leave-requests" class="<%= "review-leave-requests".equals(request.getParameter("section")) ? "active" : "" %>">请假管理</a>
            <a href="dashboard.jsp?section=registration_review" class="<%= "registration_review".equals(request.getParameter("section")) ? "active" : "" %>">审核报名</a>
            <a href="dashboard.jsp?section=fee_paymentadmin" class="<%= "fee_paymentadmin".equals(request.getParameter("section")) ? "active" : "" %>">管理员支付与费用</a>
            <%
            } else if ("user".equals(userRole)) {
            %>
            <a href="dashboard.jsp?section=studentinfo" class="<%= "studentinfo".equals(request.getParameter("section")) || request.getParameter("section") == null ? "active" : "" %>">考证进度</a>
            <a href="dashboard.jsp?section=profile" class="<%= "profile".equals(request.getParameter("section")) || request.getParameter("section") == null ? "active" : "" %>">个人资料</a>
            <span style="font-size: 16px;">课程</span>
            <ul class="nav nav-subnav">
                <li> <a href="dashboard.jsp?section=student_select_lessen" class="<%= "student_select_lessen".equals(request.getParameter("section")) || request.getParameter("section") == null ? "active" : "" %>">课程信息</a> </li>
                <li> <a href="dashboard.jsp?section=student_selected_lessen" class="<%= "student_selected_lessen".equals(request.getParameter("section")) || request.getParameter("section") == null ? "active" : "" %>">选课记录</a> </li>
            </ul>
            <ul class="nav nav-subnav">
                <li> <a href="dashboard.jsp?section=student_select_exam" class="<%= "student_select_exam".equals(request.getParameter("section")) || request.getParameter("section") == null ? "active" : "" %>">考试信息</a> </li>
                <li> <a href="dashboard.jsp?section=student_selected_exam" class="<%= "student_selected_exam".equals(request.getParameter("section")) || request.getParameter("section") == null ? "active" : "" %>">考试记录</a> </li>
            </ul>
            <a href="dashboard.jsp?section=fee_payment" class="<%= "fee_payment".equals(request.getParameter("section")) ? "active" : "" %>">费用与支付</a>
            <a href="dashboard.jsp?section=student_registration" class="<%= "student_registration".equals(request.getParameter("section")) ? "active" : "" %>">考生报名</a>
            <a href="dashboard.jsp?section=my_registration" class="<%= "my_registration".equals(request.getParameter("section")) ? "active" : "" %>">查看报名信息</a>
            <a href="dashboard.jsp?section=student_time" class="<%= "student_time".equals(request.getParameter("section")) ? "active" : "" %>">预约练车</a>
            <%
            } else if ("coach".equals(userRole)) {
            %>
            <a href="dashboard.jsp?section=mystudent" class="<%= "mystudent".equals(request.getParameter("section")) || request.getParameter("section") == null ? "active" : "" %>">我的学员</a>
            <a href="dashboard.jsp?section=coach-time" class="<%= "coach-time".equals(request.getParameter("section")) || request.getParameter("section") == null ? "active" : "" %>">预约管理</a>
            <a href="dashboard.jsp?section=profile" class="<%= "profile".equals(request.getParameter("section")) || request.getParameter("section") == null ? "active" : "" %>">个人资料</a>
            <a href="dashboard.jsp?section=coach_lessen" class="<%= "coach_lessen".equals(request.getParameter("section")) ? "active" : "" %>">我的课程</a>
            <a href="dashboard.jsp?section=coach_exam" class="<%= "coach_exam".equals(request.getParameter("section")) ? "active" : "" %>">我的考试</a>
            <a href="dashboard.jsp?section=leave-requests" class="<%= "leave-requests".equals(request.getParameter("section")) ? "active" : "" %>">请假管理</a>
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
    %><jsp:include page="UserManageView/profile.jsp" /> <%
    }else if ("usermanage".equals(section)) {
    %><jsp:include page="UserManageView/user-management.jsp" /> <%
    }else if ("assignstudent".equals(section)) {
    %><jsp:include page="assign-student.jsp" /> <%
    }else if ("studentinfo".equals(section)) {
    %><jsp:include page="studentinfo.jsp" /> <%
    }else if ("student_time".equals(section)) {
    %><jsp:include page="student_time.jsp" /> <%
    }else if ("mystudent".equals(section)) {
    %><jsp:include page="mystudent.jsp" /> <%
    }else if ("coach-time".equals(section)) {
    %><jsp:include page="coach-time.jsp" /> <%
    }else if ("student_select_lessen".equals(section)) {
    %><jsp:include page="student_select_lessen.jsp" /> <%
    }else if ("student_selected_lessen".equals(section)) {
    %><jsp:include page="student_selected_lessen.jsp" /> <%
    }else if ("coach_lessen".equals(section)) {
    %><jsp:include page="coach_lessen.jsp" /> <%
    }else if ("student_select_exam".equals(section)) {
    %><jsp:include page="student_select_exam.jsp" /> <%
    }else if ("student_selected_exam".equals(section)) {
    %><jsp:include page="student_selected_exam.jsp" /> <%
    }else if ("coach_exam".equals(section)) {
    %><jsp:include page="coach_exam.jsp" /> <%
    }else if ("leave-requests".equals(section)) {
    %><jsp:include page="leave-requests.jsp" /> <%
    }else if ("review-leave-requests".equals(section)) {
    %><jsp:include page="review-leave-requests.jsp" /> <%
    }else if ("fee_payment".equals(section)) {
    %><jsp:include page="fee_payment.jsp" />
        <%} else if ("student_registration".equals(section)) {
        %><jsp:include page="student_registration.jsp" />
        <%} else if ("registration_review".equals(section)) {
        %><jsp:include page="registration_review.jsp" />
        <%} else if ("my_registration".equals(section)) {
        %><jsp:include page="my_registration.jsp" />
        <%} else if ("fee_paymentadmin".equals(section)) {
        %><jsp:include page="fee_paymentadmin.jsp" />
        <%}else {
        %><jsp:include page="home.jsp" /> <%
        }
    %>
    </div>
</div>
</body>
</html>
