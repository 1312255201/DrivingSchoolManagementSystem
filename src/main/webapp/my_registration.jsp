<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>我的报名信息</title>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <style>
    :root {
      --primary-color: #3498db;
      --secondary-color: #2ecc71;
      --danger-color: #e74c3c;
      --text-color: #333;
      --background-color: #f4f4f4;
      --card-background: #ffffff;
      --border-color: #ddd;
    }

    .container {
      max-width: 800px;
      margin: 20px auto;
      padding: 20px;
    }

    h2 {
      color: var(--primary-color);
      text-align: center;
      margin-bottom: 20px;
    }

    .registration-info {
      background-color: var(--card-background);
      border: 1px solid var(--border-color);
      border-radius: 8px;
      padding: 20px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .info-row {
      display: flex;
      justify-content: space-between;
      margin: 10px 0;
      padding: 10px 0;
      border-bottom: 1px solid var(--border-color);
    }

    .info-row:last-child {
      border-bottom: none;
    }

    .info-label {
      font-weight: bold;
      color: var(--primary-color);
    }

    .button-group {
      display: flex;
      justify-content: flex-end;
      margin-top: 20px;
    }

    .btn {
      padding: 10px 20px;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      font-size: 16px;
      transition: background-color 0.3s, transform 0.1s;
    }

    .btn:hover {
      transform: translateY(-2px);
    }

    .btn:active {
      transform: translateY(0);
    }

    .edit-btn {
      background-color: var(--secondary-color);
      color: white;
      margin-right: 10px;
    }

    .delete-btn {
      background-color: var(--danger-color);
      color: white;
    }

    .edit-form {
      display: none;
      margin-top: 20px;
      background-color: var(--card-background);
      border: 1px solid var(--border-color);
      border-radius: 8px;
      padding: 20px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    .form-group {
      margin-bottom: 15px;
    }

    .form-group label {
      display: block;
      margin-bottom: 5px;
      color: var(--primary-color);
    }

    .form-group input {
      width: 100%;
      padding: 10px;
      border: 1px solid var(--border-color);
      border-radius: 4px;
      font-size: 16px;
    }

    .save-btn {
      background-color: var(--primary-color);
      color: white;
    }

    .loading {
      text-align: center;
      padding: 20px;
      font-style: italic;
      color: var(--text-color);
    }

    .error-message {
      background-color: #fce4e4;
      border: 1px solid #fcc2c3;
      padding: 20px;
      border-radius: 8px;
      color: #cc0033;
      text-align: center;
    }

    @media (max-width: 600px) {
      .container {
        padding: 10px;
      }

      .info-row {
        flex-direction: column;
      }

      .button-group {
        flex-direction: column;
      }

      .btn {
        width: 100%;
        margin-bottom: 10px;
      }

      .edit-btn {
        margin-right: 0;
      }
    }
  </style>
</head>
<body>
<div class="container">
  <h2>我的报名信息</h2>
  <div id="registrationInfo" class="loading">加载中...</div>
</div>

<script>
  $(document).ready(function() {
    loadMyRegistration();
  });

  function loadMyRegistration() {
    $.ajax({
      url: 'student/my-registration',
      method: 'GET',
      success: function(data) {
        console.log('收到数据:', data);
        const container = $('#registrationInfo');
        container.removeClass('loading');

        if (!data) {
          container.html('<p class="error-message">暂无报名信息</p>');
          return;
        }

        var html = '<div class="registration-info">' +
                '<div class="info-row"><span class="info-label">姓名</span>' + data.name + '</div>' +
                '<div class="info-row"><span class="info-label">电话</span>' + data.phone + '</div>' +
                '<div class="info-row"><span class="info-label">邮箱</span>' + data.email + '</div>' +
                '<div class="info-row"><span class="info-label">报名时间</span>' + data.createdAtFormatted + '</div>' +
                '<div class="info-row"><span class="info-label">状态</span>' + data.registrationStatus + '</div>' +
                '<div class="button-group">' +
                '<button class="btn edit-btn" onclick="showEditForm()">修改信息</button>' +
                '<button class="btn delete-btn" onclick="deleteRegistration(' + data.studentId + ')">删除报名</button>' +
                '</div>' +
                '</div>';

        // 添加编辑表单
        html += '<div id="editForm" class="edit-form">' +
                '<div class="form-group">' +
                '<label for="editName">姓名</label>' +
                '<input type="text" id="editName" value="' + data.name + '">' +
                '</div>' +
                '<div class="form-group">' +
                '<label for="editPhone">电话</label>' +
                '<input type="text" id="editPhone" value="' + data.phone + '">' +
                '</div>' +
                '<div class="form-group">' +
                '<label for="editEmail">邮箱</label>' +
                '<input type="email" id="editEmail" value="' + data.email + '">' +
                '</div>' +
                '<button class="btn save-btn" onclick="updateRegistration(' + data.studentId + ')">保存修改</button>' +
                '</div>';

        container.html(html);
      },
      error: function(xhr, status, error) {
        console.error('加载数据失败:', error);
        $('#registrationInfo').removeClass('loading').html('<p class="error-message">加载数据失败，请稍后重试</p>');
      }
    });
  }

  function showEditForm() {
    $('#editForm').slideToggle();
  }

  function updateRegistration(studentId) {
    var updatedData = {
      studentId: studentId,
      name: $('#editName').val(),
      phone: $('#editPhone').val(),
      email: $('#editEmail').val()
    };

    $.ajax({
      url: 'student/update-registration',
      method: 'POST',
      data: updatedData,
      success: function(response) {
        if (response.success) {
          showNotification('信息更新成功', 'success');
          loadMyRegistration();
        } else {
          showNotification('更新失败：' + (response.message || '请稍后重试'), 'error');
        }
      },
      error: function() {
        showNotification('系统错误，请稍后重试', 'error');
      }
    });
  }

  function deleteRegistration(studentId) {
    if (!confirm('确定要删除报名信息吗？此操作不可恢复。')) {
      return;
    }

    $.ajax({
      url: 'student/delete-registration',
      method: 'POST',
      data: { studentId: studentId },
      success: function(response) {
        if (response.success) {
          showNotification('报名信息已删除', 'success');
          setTimeout(function() {
            window.location.href = 'index.jsp'; // 删除后跳转到首页
          }, 2000);
        } else {
          showNotification('删除失败：' + (response.message || '请稍后重试'), 'error');
        }
      },
      error: function() {
        showNotification('系统错误，请稍后重试', 'error');
      }
    });
  }

  function showNotification(message, type) {
    const notificationId = 'notification-' + Date.now();
    const notificationHtml = '<div id="' + notificationId + '" class="notification ' + type + '">' + message + '</div>';
    $('body').append(notificationHtml);

    const notification = $('#' + notificationId);
    notification.css({
      position: 'fixed',
      top: '20px',
      right: '20px',
      padding: '10px 20px',
      borderRadius: '5px',
      color: '#fff',
      zIndex: 1000,
      opacity: 0,
      transition: 'opacity 0.3s ease-in-out'
    });

    if (type === 'success') {
      notification.css('background-color', '#2ecc71');
    } else if (type === 'error') {
      notification.css('background-color', '#e74c3c');
    }

    setTimeout(function() {
      notification.css('opacity', 1);
    }, 100);

    setTimeout(function() {
      notification.css('opacity', 0);
      setTimeout(function() {
        notification.remove();
      }, 300);
    }, 3000);
  }
</script>
</body>
</html>