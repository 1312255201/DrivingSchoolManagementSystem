/**
 * 异步函数，用于获取请假请求列表并将其显示在页面上的表格中
 * 该函数会向服务器发送请求，获取请假请求列表的 JSON 数据，并将其显示在页面上的表格中
 * 如果没有请假请求，则显示相应的提示信息
 */
async function fetchLeaveRequests() {
    // 向服务器发送请求，获取请假请求列表
    const response = await fetch('admin-view-leave-requests');
    // 解析服务器返回的 JSON 数据
    const data = await response.json();
    // 获取页面上用于显示请假请求列表的表格的 tbody 元素
    const tableBody = document.getElementById('leave-requests-tbody');
    // 清空表格的内容
    tableBody.innerHTML = '';
    // 如果没有请假请求，显示提示信息
    if (data.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="8">暂无请假请求</td></tr>';
    } else {
        // 遍历请假请求列表
        data.forEach(request => {
            // 为每个请假请求创建一个表格行，并将其添加到表格中
            tableBody.innerHTML += `
                <tr>
                    <td>${request.id}</td>
                    <td>${request.coach_name}</td>
                    <td>${request.reason}</td>
                    <td>${request.start_date}</td>
                    <td>${request.end_date}</td>
                    <td>${request.status}</td>
                    <td>${request.created_at}</td>
                    <td>
                        <button class="approve-btn" onclick="reviewRequest(${request.id}, 'approved')">批准</button>
                        <button class="reject-btn" onclick="reviewRequest(${request.id}, 'rejected')">拒绝</button>
                        <button onclick="downloadProof(${request.id})">下载佐证</button>
                    </td>
                </tr>
            `;
        });
    }
}


function downloadProof(requestId) {
    window.location.href = `download-proof?id=${requestId}`;
}


/**
 * 异步函数，用于处理管理员对请假请求的审核操作
 * @param {number} id - 请假请求的唯一标识符
 * @param {string} action - 审核操作，可选值为 'approved' 或 'rejected'
 * 该函数会向服务器发送一个 POST 请求，请求审核指定的请假请求，并根据服务器返回的结果显示相应的提示信息
 * 如果审核成功，则刷新页面以更新请假请求列表
 */
async function reviewRequest(id, action) {
    // 弹出一个输入框，让管理员输入审核意见
    const comments = prompt('请输入审核意见:');
    // 如果管理员没有输入审核意见，则弹出提示信息并返回
    if (!comments) return alert('审核意见不能为空！');
    // 向服务器发送 POST 请求，请求审核指定的请假请求
    const response = await fetch('admin-review-leave-request', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `id=${id}&action=${action}&comments=${encodeURIComponent(comments)}`
    });
    // 解析服务器返回的 JSON 数据
    const result = await response.json();
    // 弹出提示信息，显示服务器返回的结果
    alert(result.message);
    // 如果审核成功，则刷新页面以更新请假请求列表
    if (result.success) fetchLeaveRequests();
}


window.onload = fetchLeaveRequests;