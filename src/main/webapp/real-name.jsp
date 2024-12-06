<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>用户实名信息填写</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f9;
      margin: 0;
      padding: 0;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }
    .container {
      background: #fff;
      border-radius: 8px;
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
      width: 100%;
      max-width: 400px;
      padding: 20px;
      box-sizing: border-box;
    }
    .container h1 {
      margin: 0 0 20px;
      font-size: 24px;
      text-align: center;
      color: #333;
    }
    .form-group {
      margin-bottom: 15px;
    }
    .form-group label {
      display: block;
      font-size: 14px;
      color: #555;
      margin-bottom: 5px;
    }
    .form-group input {
      width: 100%;
      padding: 10px;
      font-size: 14px;
      border: 1px solid #ddd;
      border-radius: 5px;
      box-sizing: border-box;
    }
    .form-group input:focus {
      border-color: #007bff;
      outline: none;
      box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
    }
    .btn-submit {
      display: block;
      width: 100%;
      padding: 10px;
      font-size: 16px;
      font-weight: bold;
      color: #fff;
      background-color: #007bff;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      text-align: center;
    }
    .btn-submit:hover {
      background-color: #0056b3;
    }
  </style>
</head>
<body>
<div class="container">
  <h1>实名信息填写</h1>
  <form action="CompleteRealNameServlet" method="post" enctype="multipart/form-data">
    <div class="form-group">
      <label for="name">真实姓名</label>
      <input type="text" id="name" name="name" placeholder="请输入真实姓名" required>
    </div>
    <div class="form-group">
      <label for="idnumber">身份证号</label>
      <input type="text" id="idnumber" name="idnumber" placeholder="请输入身份证号" required>
    </div>
    <div class="form-group">
      <label for="avatar">上传证件照</label>
      <input type="file" id="avatar" name="avatar" accept="image/*" required>
    </div>
    <button type="submit" class="btn-submit">提交</button>
  </form>
</div>
</body>
</html>
