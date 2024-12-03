<%--
  Created by IntelliJ IDEA.
  User: AFish
  Date: 2024/12/3
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>驾校管理系统</title>
  <style>
    body {
      margin: 0;
      padding: 0;
      font-family: Arial, sans-serif;
      background-color: #f3f4f6;
      color: #333;
    }

    header {
      background-color: #007BFF;
      color: white;
      padding: 15px 20px;
      text-align: center;
      font-size: 24px;
      font-weight: bold;
    }

    main {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      height: calc(100vh - 140px);
    }

    .hero {
      text-align: center;
    }

    .hero h1 {
      font-size: 36px;
      margin-bottom: 10px;
    }

    .hero p {
      font-size: 18px;
      margin-bottom: 20px;
    }

    .carousel {
      width: 80%;
      max-width: 800px;
      height: 300px;
      margin: 20px auto;
      overflow: hidden;
      border-radius: 10px;
      box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
    }

    .carousel-images {
      display: flex;
      transition: transform 0.5s ease-in-out;
    }

    .carousel-images img {
      width: 100%;
      flex-shrink: 0;
    }

    .hero a {
      display: inline-block;
      padding: 10px 20px;
      margin: 5px;
      background-color: #007BFF;
      color: white;
      text-decoration: none;
      border-radius: 5px;
      font-size: 16px;
      transition: background-color 0.3s;
    }

    .hero a:hover {
      background-color: #0056b3;
    }

    footer {
      text-align: center;
      padding: 10px;
      background-color: #007BFF;
      color: white;
      position: fixed;
      bottom: 0;
      width: 100%;
    }
  </style>
</head>
<body>
<header>
  美林驾校
</header>
<main>
  <div class="hero">
    <h1>美林驾校</h1>
    <p>从报名到拿证一步到位。</p>
  </div>

  <div class="hero">
    <a href="login.jsp">登录系统</a>
    <a href="register.jsp">报名驾校</a>
  </div>
</main>
<footer>
  驾校管理系统 &copy; 2024
</footer>

<script>
  // 轮播图 JavaScript
  const carousel = document.querySelector('.carousel-images');
  const images = document.querySelectorAll('.carousel-images img');
  let index = 0;

  function showNextImage() {
    index = (index + 1) % images.length; // 循环显示
    const offset = -index * 100; // 偏移量
    carousel.style.transform = `translateX(${offset}%)`;
  }

  // 每3秒切换图片
  setInterval(showNextImage, 3000);
</script>
</body>
</html>
