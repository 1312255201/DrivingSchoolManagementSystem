<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>美林驾校系统 - 费用与支付模块</title>
    <style>

        /* 退款按钮样式 */
        .refund-btn {
            background-color: #ff4444;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 3px;
            cursor: pointer;
        }

        .refund-btn:hover {
            background-color: #cc0000;
        }

        /* 模态框样式 */
        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.5);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 10% auto;
            padding: 20px;
            border-radius: 5px;
            width: 50%;
            max-width: 500px;
            position: relative;
        }

        .modal-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .close {
            font-size: 24px;
            cursor: pointer;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        .form-text {
            color: #666;
            font-size: 12px;
            margin-top: 5px;
        }

        textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .button-group {
            text-align: right;
            margin-top: 20px;
        }

        .button-group button {
            margin-left: 10px;
        }

        .container {
            max-width: 1200px;
            margin: auto;
            background: #ffffff;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
        }

        h1, h2 {
            color: #34495e;
            border-bottom: 2px solid #eef2f7;
            padding-bottom: 10px;
            margin-top: 30px;
        }

        h1 {
            font-size: 28px;
            text-align: center;
            margin-bottom: 30px;
        }

        h2 {
            font-size: 22px;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            background: #ffffff;
            border-radius: 8px;
            overflow: hidden;
        }

        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #eef2f7;
        }

        th {
            background-color: #f8fafc;
            color: #606f7b;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 0.9em;
        }

        tr:hover {
            background-color: #f8fafc;
        }

        button {
            background-color: #3498db;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 6px;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 14px;
            margin: 5px;
        }

        button:hover {
            background-color: #2980b9;
            transform: translateY(-1px);
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        input[type="text"],
        input[type="number"],
        select,
        textarea {
            width: 100%;
            padding: 10px;
            margin: 8px 0;
            border: 1px solid #ddd;
            border-radius: 6px;
            box-sizing: border-box;
            transition: border-color 0.3s ease;
        }

        input[type="text"]:focus,
        input[type="number"]:focus,
        select:focus,
        textarea:focus {
            border-color: #3498db;
            outline: none;
            box-shadow: 0 0 5px rgba(52,152,219,0.2);
        }

        form {
            background: #f8fafc;
            padding: 20px;
            border-radius: 8px;
            margin: 20px 0;
        }

        label {
            display: block;
            margin-bottom: 5px;
            color: #606f7b;
            font-weight: 500;
        }

        .form-group {
            margin-bottom: 15px;
        }

        #add-edit-modal {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            margin-top: 20px;
        }

        a {
            color: #3498db;
            text-decoration: none;
            transition: color 0.3s ease;
        }

        a:hover {
            color: #2980b9;
        }

        .status-badge {
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: 500;
        }

        .status-paid {
            background-color: #dff0d8;
            color: #3c763d;
        }

        .status-pending {
            background-color: #fcf8e3;
            color: #8a6d3b;
        }

        .button-group {
            display: flex;
            gap: 10px;
            margin: 20px 0;
        }

        .primary-button {
            background-color: #3498db;
        }

        .success-button {
            background-color: #2ecc71;
        }

        .warning-button {
            background-color: #f1c40f;
        }

        .danger-button {
            background-color: #e74c3c;
        }

        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.5);
            z-index: 1000;
        }

        .modal-content {
            position: relative;
            background-color: #fff;
            margin: 15% auto;
            padding: 20px;
            width: 80%;
            max-width: 500px;
            border-radius: 5px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
        }

        .button-group {
            text-align: right;
            margin-top: 20px;
        }

        .button-group button {
            margin-left: 10px;
        }

        .edit-btn {
            padding: 5px 10px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 3px;
            cursor: pointer;
        }

        .edit-btn:hover {
            background-color: #45a049;
        }

        .payment-records {
            margin-top: 30px;
        }

        #payment-records-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        #payment-records-table th,
        #payment-records-table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        #payment-records-table th {
            background-color: #f4f4f4;
        }

        #payment-records-table tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        #payment-records-table tr:hover {
            background-color: #f5f5f5;
        }
    </style>
    <!-- 引入 jQuery -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        // 定义全局的上下文路径变量
        var contextPath = '${pageContext.request.contextPath}';

        // 确保函数在全局作用域
        window.editFee = function(feeId) {
            console.log('开始编辑费用，ID:', feeId);

            $.ajax({
                url: contextPath + '/fee/get/' + feeId,
                type: 'GET',
                dataType: 'json',
                success: function(fee) {
                    $('#fee-id').val(fee.feeId);
                    $('#fee-type').val(fee.feeType);
                    $('#fee-amount').val(fee.amount);
                    $('#fee-installment').prop('checked', fee.isInstallment);

                    if (fee.isInstallment) {
                        $('#installment-count').val(fee.installmentCount);
                        $('#installment-count-group').show();
                    } else {
                        $('#installment-count-group').hide();
                    }

                    // 处理支付方式
                    try {
                        var paymentOptions = fee.paymentOptions;
                        if (typeof paymentOptions === 'string') {
                            // 如果是JSON字符串，尝试解析
                            paymentOptions = JSON.parse(paymentOptions);
                        }
                        if (Array.isArray(paymentOptions)) {
                            $('#payment-options').val(paymentOptions);
                        } else {
                            $('#payment-options').val([paymentOptions]);
                        }
                    } catch (e) {
                        console.error('解析支付方式时出错:', e);
                        $('#payment-options').val([]);
                    }

                    $('#add-edit-modal').show();
                },
                error: function(xhr, status, error) {
                    console.error('获取费用详情失败:', error);
                    alert('获取费用详情失败：' + error);
                }
            });
        };

        // 修改渲染表格函数中的按钮生成部分
        function renderFeesTable() {
            $.ajax({
                url: contextPath + '/fee/list',
                type: 'GET',
                dataType: 'json',
                success: function(fees) {
                    console.log('获取到的费用列表:', fees);

                    if (!Array.isArray(fees)) {
                        console.error('返回的数据不是数组:', fees);
                        alert('获取费用列表失败：数据格式错误');
                        return;
                    }

                    var tableHtml = '<table class="fee-table">' +
                        '<thead>' +
                        '<tr>' +
                        '<th>费用类型</th>' +
                        '<th>金额(元)</th>' +
                        '<th>是否分期</th>' +
                        '<th>分期期数</th>' +
                        '<th>支付方式</th>' +
                        '<th>创建时间</th>' +
                        '<th>操作</th>' +
                        '</tr>' +
                        '</thead>' +
                        '<tbody>';

                    fees.forEach(function(fee) {
                        var feeType = fee.feeType || '';
                        var amount = fee.amount != null ? fee.amount : '0';
                        var isInstallment = fee.isInstallment === true ? '是' : '否';
                        var installmentCount = fee.installmentCount || '1';
                        var paymentOptions = fee.paymentOptions || '';
                        var createdAt = fee.createdAt ? new Date(fee.createdAt).toLocaleString('zh-CN') : '';

                        tableHtml += '<tr>' +
                            '<td>' + feeType + '</td>' +
                            '<td>' + amount + '</td>' +
                            '<td>' + isInstallment + '</td>' +
                            '<td>' + installmentCount + '</td>' +
                            '<td>' + paymentOptions + '</td>' +
                            '<td>' + createdAt + '</td>' +
                            '<td>' +
                            '<button type="button" class="edit-btn" onclick="window.editFee(' + fee.feeId + ')">编辑</button>' +
                            '</td>' +
                            '</tr>';
                    });

                    tableHtml += '</tbody></table>';
                    $('#fees-table').html(tableHtml);
                },
                error: function(xhr, status, error) {
                    console.error('获取费用列表失败:', error);
                    console.error('状态码:', xhr.status);
                    console.error('响应文本:', xhr.responseText);
                    try {
                        const response = JSON.parse(xhr.responseText);
                        alert('获取费用列表失败：' + (response.error || error));
                    } catch(e) {
                        alert('获取费用列表失败：' + error);
                    }
                }
            });
        }

        // 确保其他函数也在全局作用域
        window.saveFee = function() {
            var feeId = $('#fee-id').val();
            var isEdit = feeId !== '';

            // 获取支付方式并正确格式化
            var paymentOptionsArray = $('#payment-options').val() || [];

            var feeData = {
                feeId: feeId,
                feeType: $('#fee-type').val(),
                amount: $('#fee-amount').val(),
                isInstallment: $('#fee-installment').is(':checked'),
                installmentCount: $('#fee-installment').is(':checked') ? $('#installment-count').val() : 1,
                // 将支付方式作为单个字符串传递
                paymentOptions: paymentOptionsArray.length > 0 ? paymentOptionsArray[0] : ''
            };

            console.log('提交的数据:', feeData);
            var requestUrl = contextPath + '/fee/update';
            console.log('完整请求URL:', requestUrl);

            $.ajax({
                url: requestUrl,
                type: 'POST',
                data: feeData,
                success: function(response) {
                    console.log('收到服务器响应:', response);
                    if (response.success) {
                        alert('费用更新成功！');
                        $('#add-edit-modal').hide();
                        renderFeesTable();
                    } else {
                        alert('费用更新失败：' + (response.message || '未知错误'));
                    }
                },
                error: function(xhr, status, error) {
                    console.error('保存费用失败:', error);
                    console.error('状态码:', xhr.status);
                    console.error('响应文本:', xhr.responseText);
                    alert('���存费用失败：' + error);
                }
            });
        };

        // 页面加载完成后的初始化
        $(document).ready(function() {
            console.log('页面初始化...');
            renderFeesTable();

            $('#save-fee-btn').click(function() {
                window.saveFee();
            });

            $('#fee-installment').change(function() {
                $('#installment-count-group').toggle(this.checked);
            });

            $('.modal-close, #cancel-fee-btn').click(function() {
                $('#add-edit-modal').hide();
            });

            // 添加新增��用按钮的点击事件处理
            $('#add-fee-btn').click(function() {
                // 清空表单
                $('#fee-id').val('');
                $('#fee-type').val('');
                $('#fee-amount').val('');
                $('#fee-installment').prop('checked', false);
                $('#installment-count').val('2');
                $('#payment-options').val([]);
                $('#installment-count-group').hide();

                // 显示模态框
                $('#add-edit-modal').show();
                // 设置标题
                $('#modal-title').text('添加费用');
            });

            // 确保保存按钮使用 saveFee 函数
            $('#save-fee-btn, button[onclick="window.saveFee()"]').click(function(e) {
                e.preventDefault();
                window.saveFee();
            });

            // 页面加载时获取所有支付记录
            loadPaymentRecords();

            // 当学员ID输入框值改变时，重新加载该学员的支付记录
            $('#learner-id').on('change', function() {
                var studentId = $(this).val();
                if (studentId) {
                    loadPaymentRecordsByStudent(studentId);
                } else {
                    loadPaymentRecords();
                }
            });

            // 添加缴费按钮的点击事件处理
            $('#make-payment-btn').click(function() {
                var studentId = $('#learner-id').val();
                var feeId = $('#payment-fee-id').val();
                var amount = $('#payment-amount').val();
                var paymentMethod = $('#payment-method').val();

                // 验证输入
                if (!studentId || !feeId || !amount || !paymentMethod) {
                    alert('请填写所有必填字段');
                    return;
                }

                // 创建支付记录
                $.ajax({
                    url: contextPath + '/payment/create',
                    type: 'POST',
                    data: {
                        studentId: studentId,
                        feeId: feeId,
                        amount: amount,
                        paymentMethod: paymentMethod
                    },
                    success: function(response) {
                        if (response.success) {
                            alert('缴费成功！');
                            // 清空表单
                            $('#payment-form')[0].reset();
                            // 重新加载支付记录
                            loadPaymentRecords();
                        } else {
                            alert('缴费失败：' + (response.message || '未知错误'));
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error('缴费失败:', error);
                        console.error('状态码:', xhr.status);
                        console.error('响应文本:', xhr.responseText);
                        alert('缴费失败：' + error);
                    }
                });
            });
        });
        // 确保在document ready中添加这段代码
        $(document).ready(function() {
            console.log("页面加载完成，开始绑定事件");

            // 绑定提交按钮的点击事件
            $('#submit-refund-btn').on('click', function(e) {
                console.log("提交退款按钮被点击");
                e.preventDefault(); // 防止表单默认提交

                // 获取表单数据
                var paymentId = $('#refund-payment-id').val();
                var refundAmount = $('#refund-amount').val();
                var refundReason = $('#refund-reason').val();

                console.log("退款表单数据:", {
                    paymentId: paymentId,
                    refundAmount: refundAmount,
                    refundReason: refundReason
                });

                // 表单验证
                if (!paymentId || !refundAmount || !refundReason) {
                    alert('请填写所有必填字段');
                    return;
                }

                // 发送退款申请
                $.ajax({
                    url: contextPath + '/refund/create',
                    type: 'POST',
                    data: {
                        paymentId: paymentId,
                        refundAmount: refundAmount,
                        refundReason: refundReason
                    },
                    success: function(response) {
                        console.log('收到服务器响应:', response);
                        if (response.success) {
                            alert('退款申请提交成功！');
                            $('#refund-modal').hide();
                            $('#refund-form')[0].reset();
                            loadRefundRecords(); // 刷新退款记录列表
                        } else {
                            alert('退款申请提交失败：' + (response.message || '未知错误'));
                        }
                    },
                    error: function(xhr, status, error) {
                        console.error('退款申请失败:', {
                            status: status,
                            error: error,
                            response: xhr.responseText
                        });
                        alert('退款申请失败：' + error);
                    }
                });
            });

            // 添加模态框的关闭事件
            $('.close, .btn-secondary').on('click', function() {
                console.log("关闭模态框");
                $('#refund-modal').hide();
            });
        });
        // 加载所有支付记录
        function loadPaymentRecords() {
            $.ajax({
                url: contextPath + '/payment/list',
                type: 'GET',
                success: function(records) {
                    renderPaymentRecords(records);
                },
                error: function(xhr, status, error) {
                    console.error('获取支付记录失败:', error);
                    alert('获取支付记录失败：' + error);
                }
            });
        }

        // 加载特定学员的支付记录
        function loadPaymentRecordsByStudent(studentId) {
            $.ajax({
                url: contextPath + '/payment/student/' + studentId,
                type: 'GET',
                success: function(records) {
                    renderPaymentRecords(records);
                },
                error: function(xhr, status, error) {
                    console.error('获取学员支付记录失败:', error);
                    alert('获取学员支付记录失败：' + error);
                }
            });
        }

        // 渲染支付记录表格
        function renderPaymentRecords(records) {
            var tbody = $('#payment-records-table tbody');
            tbody.empty();

            if (records && records.length > 0) {
                records.forEach(function(record) {
                    var row = $('<tr>');
                    row.append($('<td>').text(record.paymentId));
                    row.append($('<td>').text(record.studentId));
                    row.append($('<td>').text(record.feeType));
                    row.append($('<td>').text(record.amount));
                    row.append($('<td>').text(record.paymentMethod));
                    row.append($('<td>').text(new Date(record.paymentTime).toLocaleString()));
                    row.append($('<td>').text(record.paymentStatus));
                    tbody.append(row);
                });
            } else {
                tbody.append($('<tr>')
                    .append($('<td colspan="7" style="text-align: center;">')
                        .text('暂无支付记录')));
            }
        }

        // 修改支付记录的渲染函数，添加调试信息
        function renderPaymentRecords(records) {
            console.log("开始渲染支付记录:", records); // 添加日志
            var tbody = $('#payment-records-table tbody');
            tbody.empty();

            if (records && records.length > 0) {
                records.forEach(function(record) {
                    console.log("处理记录:", record); // 添加日志
                    var row = $('<tr>');
                    row.append($('<td>').text(record.paymentId));
                    row.append($('<td>').text(record.studentId));
                    row.append($('<td>').text(record.feeType));
                    row.append($('<td>').text(record.amount));
                    row.append($('<td>').text(record.paymentMethod));
                    row.append($('<td>').text(new Date(record.paymentTime).toLocaleString()));
                    row.append($('<td>').text(record.paymentStatus));

                    // 添加退款按钮
                    var refundButton = $('<button>')
                        .text('申请退款')
                        .addClass('refund-btn')
                        .click(function(e) {
                            console.log("退款按钮被点击"); // 添加日志
                            e.preventDefault(); // 防止表单提交
                            $('#refund-payment-id').val(record.paymentId);
                            $('#original-payment-amount').text(record.amount);
                            $('#refund-amount').val(record.amount);
                            $('#refund-modal').show();
                        });
                    row.append($('<td>').append(refundButton));
                    tbody.append(row);
                });
            } else {
                tbody.append($('<tr>')
                    .append($('<td colspan="8" style="text-align: center;">')
                        .text('暂无支付记录')));
            }
        }

        // 加载退款记录
        function loadRefundRecords() {
            $.ajax({
                url: contextPath + '/refund/list',
                type: 'GET',
                success: function(refunds) {
                    renderRefundRecords(refunds);
                },
                error: function(xhr, status, error) {
                    console.error('获取退款记录失败:', error);
                }
            });
        }

        // 渲染退款记录
        function renderRefundRecords(refunds) {
            var tbody = $('#refund-records-table tbody');
            tbody.empty();

            if (refunds && refunds.length > 0) {
                refunds.forEach(function(refund) {
                    var row = $('<tr>');
                    row.append($('<td>').text(refund.refundId));
                    row.append($('<td>').text(refund.paymentId));
                    row.append($('<td>').text(refund.studentId));
                    row.append($('<td>').text(refund.feeType));
                    row.append($('<td>').text(refund.originalAmount));
                    row.append($('<td>').text(refund.refundAmount));
                    row.append($('<td>').text(refund.refundReason));
                    row.append($('<td>').text(refund.refundStatus));
                    row.append($('<td>').text(new Date(refund.refundTime).toLocaleString()));
                    tbody.append(row);
                });
            } else {
                tbody.append($('<tr>')
                    .append($('<td colspan="9" style="text-align: center;">')
                        .text('暂无退款记录')));
            }
        }

        // 页面加载完成后加载退款记录
        $(document).ready(function() {
            loadRefundRecords();
        });
    </script>
</head>
<body>
<div class="container">
    <h1>美林驾校系统 - 费用与支付模块</h1>

    <!-- Payments Section -->
    <h2>支付记录管理</h2>
    <form id="payment-form">
        <label for="learner-id">学员ID:</label>
        <input type="text" id="learner-id" required>
        <label for="payment-fee-id">费用ID:</label>
        <select id="payment-fee-id" required>
            <option value="1">科一</option>
            <option value="2">科二</option>
            <option value="3">科三</option>
            <option value="4">科四</option>
            <option value="5">补考</option>
        </select>
        <label for="payment-amount">支付金额:</label>
        <input type="number" step="any" id="payment-amount" required>
        <label for="payment-method">支付方式:</label>
        <select id="payment-method" required>
            <option value="支付宝">支付宝</option>
            <option value="微信支付">微信支付</option>
            <option value="银行转账">银行转账</option>
            <option value="现金支付">现金支付</option>
        </select>
        <button type="button" id="make-payment-btn">缴费</button>
    </form>
    <div id="payments-table"></div>
    <%--<button id="request-refund-btn">申请退款</button>--%>

    <!-- 退款申请模态框 -->
    <div id="refund-modal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3>申请退款</h3>
                <span class="close">&times;</span>
            </div>
            <div class="modal-body">
                <form id="refund-form">
                    <input type="hidden" id="refund-payment-id">
                    <div class="form-group">
                        <label>原支付金额:</label>
                        <span id="original-payment-amount"></span>
                    </div>
                    <div class="form-group">
                        <label for="refund-amount">退款金额:</label>
                        <input type="number" id="refund-amount" step="0.01" required>
                        <small class="form-text">退款金额不能超过原支付金额</small>
                    </div>
                    <div class="form-group">
                        <label for="refund-reason">退款原因:</label>
                        <textarea id="refund-reason" required rows="4"></textarea>
                    </div>
                    <div class="button-group">
                        <button type="button" class="btn btn-secondary">取消</button>
                        <button type="button" class="btn btn-primary" id="submit-refund-btn">提交申请</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- 在模态框后面添加退款记录表格 -->
    <div class="refund-records">
        <h3>退款记录</h3>
        <table id="refund-records-table">
            <thead>
            <tr>
                <th>退款ID</th>
                <th>支付ID</th>
                <th>学员ID</th>
                <th>费用类型</th>
                <th>原始金额</th>
                <th>退款金额</th>
                <th>退款原因</th>
                <th>退款状态</th>
                <th>退款时间</th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>

    <!-- Refunds Section -->
    <h2>退款处理</h2>
    <div id="refunds-table"></div>

    <!-- 在支付表单下方添加支付记录表格 -->
    <div class="payment-records">
        <h3>支付记录列表</h3>
        <table id="payment-records-table">
            <thead>
            <tr>
                <th>支付ID</th>
                <th>学员ID</th>
                <th>费用类型</th>
                <th>支付金额</th>
                <th>支付方式</th>
                <th>支付时间</th>
                <th>支付状态</th>
            </tr>
            </thead>
            <tbody>
            <!-- 数据将通过JavaScript动态加载 -->
            </tbody>
        </table>
    </div>


</div>
</body>
</html>



