// 页面加载完成后执行
$(document).ready(function() {
    loadFeeSettings();
    loadRefundRequests();
    
    // 绑定事件监听
    $('#installmentAllowed').change(function() {
        $('#installmentMonths').prop('disabled', !this.checked);
    });

    // 表单提交处理
    $('#addFeeForm').submit(function(e) {
        e.preventDefault();
        saveFeeSettings();
    });
});

// 加载费用设置
function loadFeeSettings() {
    $.ajax({
        url: 'admin/fee-settings',
        method: 'GET',
        success: function(data) {
            renderFeeSettings(data);
        },
        error: function(xhr, status, error) {
            console.error('加载费用设置失败:', error);
            alert('加载费用设置失败');
        }
    });
}

// 渲染费用设置列表
function renderFeeSettings(data) {
    const feeList = $('#feeList');
    feeList.empty();
    
    data.forEach(fee => {
        const installmentInfo = fee.installmentAllowed ? 
            '<p>分期期数：' + fee.installmentMonths + '期</p>' : '';
        
        feeList.append(
            '<div class="fee-item">' +
                '<h4>' + fee.name + '</h4>' +
                '<p>金额：¥' + fee.amount + '</p>' +
                '<p>分期选项：' + (fee.installmentAllowed ? '支持' : '不支持') + '</p>' +
                installmentInfo +
                '<div class="button-group">' +
                    '<button onclick="editFee(' + fee.id + ')">编辑</button>' +
                    '<button onclick="deleteFee(' + fee.id + ')">删除</button>' +
                '</div>' +
            '</div>'
        );
    });
}

// 加载退款请求
function loadRefundRequests() {
    $.ajax({
        url: 'admin/refund-requests',
        method: 'GET',
        success: function(data) {
            renderRefundRequests(data);
        },
        error: function(xhr, status, error) {
            console.error('加载退款请求失败:', error);
            alert('加载退款请求失败');
        }
    });
}

// 渲染退款请求列表
function renderRefundRequests(data) {
    const requests = $('#refundRequests');
    requests.empty();
    
    data.forEach(refund => {
        requests.append(
            '<div class="refund-request">' +
                '<p>学员：' + refund.studentName + '</p>' +
                '<p>退款金额：¥' + refund.amount + '</p>' +
                '<p>申请原因：' + refund.reason + '</p>' +
                '<p>申请时间：' + refund.requestTime + '</p>' +
                '<div class="button-group">' +
                    '<button onclick="approveRefund(' + refund.id + ')">同意退款</button>' +
                    '<button onclick="rejectRefund(' + refund.id + ')">拒绝退款</button>' +
                '</div>' +
            '</div>'
        );
    });
}

// 保存费用设置
function saveFeeSettings() {
    const feeData = {
        name: $('#feeName').val(),
        amount: $('#feeAmount').val(),
        installmentAllowed: $('#installmentAllowed').is(':checked'),
        installmentMonths: $('#installmentMonths').val()
    };

    $.ajax({
        url: 'admin/fee-settings',
        method: 'POST',
        data: feeData,
        success: function(response) {
            if (response.success) {
                alert('费用项添加成功');
                closeAddFeeForm();
                loadFeeSettings();
            } else {
                alert('添加失败：' + response.message);
            }
        },
        error: function(xhr, status, error) {
            console.error('保存费用设置失败:', error);
            alert('保存失败');
        }
    });
}

// 模态框操作
function showAddFeeForm() {
    $('#addFeeModal').show();
}

function closeAddFeeForm() {
    $('#addFeeModal').hide();
    $('#addFeeForm')[0].reset();
}

// 费用项操作
function editFee(feeId) {
    $.ajax({
        url: 'admin/fee-settings/' + feeId,
        method: 'GET',
        success: function(fee) {
            $('#feeName').val(fee.name);
            $('#feeAmount').val(fee.amount);
            $('#installmentAllowed').prop('checked', fee.installmentAllowed);
            $('#installmentMonths').val(fee.installmentMonths);
            $('#installmentMonths').prop('disabled', !fee.installmentAllowed);
            
            showAddFeeForm();
        }
    });
}

function deleteFee(feeId) {
    if (confirm('确定要删除这个费用项吗？')) {
        $.ajax({
            url: 'admin/fee-settings/' + feeId,
            method: 'DELETE',
            success: function(response) {
                if (response.success) {
                    alert('删除成功');
                    loadFeeSettings();
                } else {
                    alert('删除失败：' + response.message);
                }
            }
        });
    }
}

// 退款操作
function approveRefund(refundId) {
    if (confirm('确定同意这个退款申请吗？')) {
        $.ajax({
            url: 'admin/refund-requests/' + refundId + '/approve',
            method: 'POST',
            success: function(response) {
                if (response.success) {
                    alert('退款申请已通过');
                    loadRefundRequests();
                } else {
                    alert('操作失败：' + response.message);
                }
            }
        });
    }
}

function rejectRefund(refundId) {
    const reason = prompt('请输入拒绝原因：');
    if (reason) {
        $.ajax({
            url: 'admin/refund-requests/' + refundId + '/reject',
            method: 'POST',
            data: { reason: reason },
            success: function(response) {
                if (response.success) {
                    alert('退款申请已拒绝');
                    loadRefundRequests();
                } else {
                    alert('操作失败：' + response.message);
                }
            }
        });
    }
}