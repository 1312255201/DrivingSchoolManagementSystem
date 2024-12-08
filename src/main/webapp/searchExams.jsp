<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*, cn.bugfish.drivingschoolmanagementsystem.DataBase.DBUtil" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>学生选课系统</title>
    <style>
        h1 { text-align: center; }
        table { width: 100%; border-collapse: collapse; margin: 20px 0; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f4f4f4; }
        .btn { padding: 5px 10px; color: #fff; border: none; cursor: pointer; border-radius: 4px; }
        .btn-select { background-color: #28a745; }
        .btn-cancel { background-color: #dc3545; }
        .btn:hover { opacity: 0.8; }
    </style>
</head>
<body>
<h1>搜索结果</h1>

<%
    String query = request.getParameter("query");  // 获取查询字符串
    if (query == null || query.trim().isEmpty()) {
        out.println("<p>请输入课程名称进行搜索。</p>");
    } else {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM exams WHERE name LIKE ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, "%" + query + "%");  // 使用 LIKE 操作符进行模糊查询
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        out.println("<p>没有找到符合条件的课程。</p>");
                    } else {
%>
<table>
    <thead>
    <tr>
        <th>考试号</th>
        <th>考试名称</th>
        <th>开始时间</th>
        <th>结束时间</th>
        <th>剩余名额</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <%
        do {
    %>
    <tr>
        <td><%= rs.getString("exam_number") %></td>
        <td><%= rs.getString("name") %></td>
        <td><%= rs.getString("start_time") %></td>
        <td><%= rs.getString("end_time") %></td>
        <td><%= rs.getInt("capacity") %></td>
        <td>
            <form action="select-exam" method="post">
                <input type="hidden" name="exam_id" value="<%= rs.getInt("id") %>">
                <input type="submit" class="btn btn-select" value="选择考试">
            </form>
        </td>
    </tr>
    <%
        } while (rs.next());
    %>
    </tbody>
</table>
<%
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
%>

</body>
</html>

