<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>报名审核管理</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        :root {
            --primary-color: #3498db;
            --success-color: #2ecc71;
            --danger-color: #e74c3c;
            --background-color: #f4f4f4;
            --card-background: #ffffff;
            --text-color: #333333;
            --border-color: #dddddd;
        }

        body {
            font-family: 'Arial', sans-serif;
            line-height: 1.6;
            color: var(--text-color);
            background-color: var(--background-color);
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 1200px;
            margin: 20px auto;
            padding: 20px;
        }

        .h2_baoming {
            color: var(--primary-color);
            text-align: center;
            margin-bottom: 20px;
        }

        .filter-container {
            margin-bottom: 20px;
            text-align: center;
        }

        #statusFilter {
            padding: 10px;
            font-size: 16px;
            border: 1px solid var(--border-color);
            border-radius: 5px;
            background-color: var(--card-background);
            color: var(--text-color);
        }

        .registration-item {
            background-color: var(--card-background);
            border: 1px solid var(--border-color);
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: box-shadow 0.3s ease;
        }

        .registration-item:hover {
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15);
        }

        .registration-item h3 {
            color: var(--primary-color);
            margin-top: 0;
        }

        .info-row {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }

        .info-label {
            font-weight: bold;
            color: var(--primary-color);
        }

        .button-group {
            display: flex;
            justify-content: flex-end;
            margin-top: 15px;
        }

        .btn {
            padding: 8px 16px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s, transform 0.1s;
        }

        .btn:hover {
            transform: translateY(-2px);
        }

        .btn:active {
            transform: translateY(0);
        }

        .approve-btn {
            background-color: var(--success-color);
            color: white;
            margin-right: 10px;
        }

        .reject-btn {
            background-color: var(--danger-color);
            color: white;
        }

        .reject-form {
            display: none;
            margin-top: 15px;
        }

        .reject-form textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid var(--border-color);
            border-radius: 5px;
            resize: vertical;
            min-height: 100px;
            margin-bottom: 10px;
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

        @media (max-width: 768px) {
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

            .approve-btn {
                margin-right: 0;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <h2 class="h2_baoming">报名审核管理</h2>

    <div class="filter-container">
        <select id="statusFilter" onchange="loadRegistrations(this.value)">
            <option value="PENDING">待审核</option>
            <option value="APPROVED">已通过</option>
            <option value="REJECTED">已拒绝</option>
        </select>
    </div>

    <div id="registrationList" class="loading">加载中...</div>
</div>

<script>
    $(document).ready(function() {
        console.log('页面加载完成');
        loadRegistrations('PENDING');
    });

    function loadRegistrations(status) {
        console.log('开始加载数据，状态:', status);
        const listContainer = $('#registrationList');
        listContainer.html('<div class="loading">加载中...</div>');

        $.ajax({
            url: 'student/registration-review',
            method: 'GET',
            data: { status: status },
            success: function(data) {
                console.log('收到数据:', data);
                listContainer.empty();

                if (!data || data.length === 0) {
                    listContainer.html('<p class="error-message">暂无数据</p>');
                    return;
                }

                data.forEach(function(student) {
                    var html = '<div class="registration-item">' +
                        '<h3>报名信息</h3>' +
                        '<div class="info-row"><span class="info-label">学生ID：</span>' + student.studentId + '</div>' +
                        '<div class="info-row"><span class="info-label">姓名：</span>' + student.name + '</div>' +
                        '<div class="info-row"><span class="info-label">电话：</span>' + student.phone + '</div>' +
                        '<div class="info-row"><span class="info-label">邮箱：</span>' + student.email + '</div>' +
                        '<div class="info-row"><span class="info-label">报名时间：</span>' + student.createdAtFormatted + '</div>' +
                        '<div class="info-row"><span class="info-label">状态：</span>' + student.registrationStatus + '</div>';

                    if (status === 'PENDING') {
                        html += '<div class="button-group">' +
                            '<button class="btn approve-btn" onclick="approveRegistration(' + student.studentId + ')">通过</button>' +
                            '<button class="btn reject-btn" onclick="showRejectForm(' + student.studentId + ')">拒绝</button>' +
                            '</div>' +
                            '<div id="rejectForm' + student.studentId + '" class="reject-form">' +
                            '<textarea id="rejectReason' + student.studentId + '" placeholder="请输入拒绝原因"></textarea>' +
                            '<button class="btn reject-btn" onclick="rejectRegistration(' + student.studentId + ')">确认拒绝</button>' +
                            '</div>';
                    }

                    if (status === 'REJECTED' && student.rejectReason) {
                        html += '<div class="info-row"><span class="info-label">拒绝原因：</span>' + student.rejectReason + '</div>';
                    }

                    html += '</div>';
                    listContainer.append(html);
                });
            },
            error: function(xhr, status, error) {
                console.error('请求失败:', error);
                listContainer.html('<p class="error-message">加载数据失败，请稍后重试</p>');
            }
        });
    }

    function approveRegistration(studentId) {
        console.log('审核通过:', studentId);
        $.ajax({
            url: 'student/registration-review/approve',
            method: 'POST',
            data: {
                studentId: studentId,
                reviewerId: '${sessionScope.userId}'
            },
            success: function(response) {
                if (response.success) {
                    showNotification('审核通过成功', 'success');
                    loadRegistrations($('#statusFilter').val());
                } else {
                    showNotification('操作失败，请稍后重试', 'error');
                }
            },
            error: function() {
                showNotification('系统错误，请稍后重试', 'error');
            }
        });
    }

    function showRejectForm(studentId) {
        $('#rejectForm' + studentId).slideToggle();
    }

    function rejectRegistration(studentId) {
        var reason = $('#rejectReason' + studentId).val();
        if (!reason) {
            showNotification('请输入拒绝原因', 'error');
            return;
        }

        $.ajax({
            url: 'student/registration-review/reject',
            method: 'POST',
            data: {
                studentId: studentId,
                reason: reason,
                reviewerId: '${sessionScope.userId}'
            },
            success: function(response) {
                if (response.success) {
                    showNotification('已拒绝该申请', 'success');
                    loadRegistrations($('#statusFilter').val());
                } else {
                    showNotification('操作失败，请稍后重试', 'error');
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