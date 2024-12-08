<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>学员报名</title>
    <style>
        /* 基础样式 */
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(to right, #66ccff, #99ff99); /* 渐变背景 */
            margin: 0;
            padding: 0;
            color: #333;
        }

        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            padding: 20px;
            box-sizing: border-box;
        }

        .form-container {
            background-color: #ffffff;
            border-radius: 12px;
            padding: 40px;
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
            width: 100%;
            max-width: 480px;
            transition: transform 0.3s ease;
        }

        .form-container:hover {
            transform: scale(1.02); /* 表单容器悬停效果 */
        }

        h1 {
            text-align: center;
            color: #4CAF50;
            font-size: 2rem;
            margin-bottom: 20px;
            font-weight: bold;
        }

        /* 表单元素样式 */
        .form-group {
            margin-bottom: 25px;
        }

        label {
            display: block;
            font-size: 1.1rem;
            color: #333;
            margin-bottom: 10px;
            font-weight: bold;
        }

        input[type="text"], input[type="email"], input[type="file"], select {
            width: 100%;
            padding: 12px;
            border: 2px solid #ddd;
            border-radius: 8px;
            font-size: 1rem;
            color: #333;
            box-sizing: border-box;
            transition: border-color 0.3s ease;
        }

        input[type="text"]:focus, input[type="email"]:focus, select:focus {
            border-color: #4CAF50;
            outline: none;
        }

        button.submit-btn {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 15px 20px;
            border-radius: 8px;
            font-size: 1.2rem;
            width: 100%;
            cursor: pointer;
            transition: background-color 0.3s ease;
            font-weight: bold;
        }

        button.submit-btn:hover {
            background-color: #45a049;
        }

        button.submit-btn:active {
            background-color: #388e3c;
        }

        /* 响应式设计 */
        @media (max-width: 600px) {
            .form-container {
                padding: 25px;
            }

            h1 {
                font-size: 1.6rem;
            }

            label {
                font-size: 1rem;
            }

            input[type="text"], input[type="email"], select, button.submit-btn {
                font-size: 1rem;
            }
        }
    </style>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<div class="container">
    <div class="form-container">
        <h1>学员报名</h1>
        <form id="registrationForm" action="submitRegistration" method="POST">
            <div class="form-group">
                <label for="student_name">学员姓名：</label>
                <input type="text" id="student_name" name="student_name" required>
            </div>

            <div class="form-group">
                <label for="phone">联系电话：</label>
                <input type="text" id="phone" name="phone" required>
            </div>

            <div class="form-group">
                <label for="email">电子邮箱：</label>
                <input type="email" id="email" name="email">
            </div>

            <div class="form-group">
                <label for="state">学习状况：</label>
                <select id="state" name="state" required>
                    <option value="新学员">新学员</option>
                    <option value="科目一">科目一</option>
                    <option value="科目二">科目二</option>
                    <option value="科目三">科目三</option>
                    <option value="科目四">科目四</option>
                    <option value="拿证">拿证</option>
                </select>
            </div>

            <div class="form-group">
                <label for="study_type">考试科目：</label>
                <input type="text" id="study_type" name="study_type" required>
            </div>

            <button type="submit" class="submit-btn">提交报名</button>
        </form>
    </div>
</div>
<script>
    $(document).ready(function() {
        console.log("页面加载完成");

        $('#registrationForm').on('submit', function(e) {
            e.preventDefault();
            console.log("表单提交事件触发");

            // 获取表单数据
            var formData = {
                student_name: $('#student_name').val(),
                phone: $('#phone').val(),
                email: $('#email').val(),
                state: $('#state').val(),
                study_type: $('#study_type').val()
            };

            console.log("准备发送的数据:", formData);

            $.ajax({
                url: 'submitRegistration',
                type: 'POST',
                data: formData,
                beforeSend: function() {
                    console.log("开始发送AJAX请求");
                },
                success: function(response) {
                    console.log("收到服务器响应:", response);
                    if (response.success) {
                        alert('报名成功！');
                        $('#registrationForm')[0].reset();
                    } else {
                        alert('报名失败：' + response.message);
                    }
                },
                error: function(xhr, status, error) {
                    console.error('AJAX请求失败:', {
                        status: status,
                        error: error,
                        responseText: xhr.responseText
                    });
                    alert('系统错误，请稍后重试');
                }
            });
        });
    });
</script>
</body>
</html>
