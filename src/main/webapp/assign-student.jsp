<%@ page import="cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2024/12/6
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>教练与学员管理</title>
  <style>
    h1 {
      text-align: center;
      color: #333;
    }
    .form-container {
      max-width: 800px;
      margin: 0 auto;
      background: #fff;
      padding: 20px;
      border-radius: 8px;
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
    }
    .form-group, .student-list {
      margin-bottom: 20px;
    }
    .form-group label {
      display: block;
      font-size: 14px;
      color: #555;
      margin-bottom: 5px;
    }
    .form-group select, .form-group button {
      padding: 10px;
      font-size: 14px;
    }
    .btn {
      padding: 10px 20px;
      font-size: 14px;
      color: #fff;
      background-color: #007bff;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }
    .btn-danger {
      background-color: #dc3545;
    }
    .btn:hover {
      opacity: 0.8;
    }
    .student-list {
      padding: 10px;
      border: 1px solid #ddd;
      border-radius: 8px;
      background: #f9f9f9;
    }
    .student-item {
      display: flex;
      justify-content: space-between;
      margin-bottom: 10px;
    }
  </style>
</head>
<body>
<h1>教练与学员管理</h1>
<div class="form-container">
  <div class="form-group">
    <label for="coach">选择教练：</label>
    <select id="coach" name="coach_id" onchange="loadStudents(this.value)">
      <option value="">请选择教练</option>
      <%
        try {
          Connection conn = DBUtil.getConnection();
          String query = "SELECT id, name FROM users WHERE role = 'coach'";
          Statement stmt = conn.createStatement();
          ResultSet rs = stmt.executeQuery(query);
          while (rs.next()) {
            out.println("<option value='" + rs.getInt("id") + "'>" + rs.getString("name") + "</option>");
          }
          conn.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      %>
    </select>
  </div>
  <div id="student-section">
    <h3>当前学员：</h3>
    <div class="student-list" id="student-list">
      请选择教练以加载学员列表
    </div>
    <div class="form-group">
      <label for="new-student">添加学员：</label>
      <select id="new-student">
        <%
          try {
            Connection conn = DBUtil.getConnection();
            String query = "SELECT id, name FROM users WHERE role = 'user'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
              out.println("<option value='" + rs.getInt("id") + "'>" + rs.getString("name") + "</option>");
            }
            conn.close();
          } catch (Exception e) {
            e.printStackTrace();
          }
        %>
      </select>
      <button class="btn" onclick="assignStudent()">添加</button>
    </div>
  </div>
</div>

<script>
  function loadStudents(coachId) {
    if (!coachId) {
      document.getElementById("student-list").innerHTML = "请选择教练以加载学员列表";
      return;
    }
    fetch(`GetStudentsServlet?coach_id=` + coachId)
            .then(response => response.json())
            .then(data => {
              const list = document.getElementById("student-list");
              list.innerHTML = "";
              if (data.length === 0) {
                list.innerHTML = "没有学员";
              } else {
                data.forEach(student => {
                  const div = document.createElement("div");
                  div.className = "student-item";
                  div.innerHTML = `
                                <span>`+student.name+` (`+student.id+`)</span>
                                <button class="btn btn-danger" onclick="removeStudent(`+student.id+`, `+coachId+`)">删除</button>
                            `;
                  list.appendChild(div);
                });
              }
            })
            .catch(err => console.error(err));
  }

  function assignStudent() {
    const coachId = document.getElementById("coach").value;
    const studentId = document.getElementById("new-student").value;

    if (!coachId || !studentId) {
      alert("请确保选择了教练和学员！");
      return;
    }

    fetch("AssignStudentServletChange", {
      method: "POST",
      headers: { "Content-Type": "application/x-www-form-urlencoded" },
      body: `coach_id=` +coachId+ `&student_id=` + studentId
    })
            .then(response => response.text())
            .then(result => alert(result))
            .then(() => loadStudents(coachId))
            .catch(err => console.error(err));
  }

  function removeStudent(studentId, coachId) {
    if (confirm("确定要删除这个学员吗？")) {
      fetch("RemoveStudentServlet", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `coach_id=` +coachId+ `&student_id=` + studentId
      })
              .then(response => response.text())
              .then(result => alert(result))
              .then(() => loadStudents(coachId))
              .catch(err => console.error(err));
    }
  }
</script>
</body>
</html>
