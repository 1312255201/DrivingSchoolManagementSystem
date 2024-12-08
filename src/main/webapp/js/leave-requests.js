/**
 * 异步函数，用于获取请假请求列表并将其显示在页面上的表格中
 * 该函数会向服务器发送请求，获取请假请求列表的 JSON 数据
 * 然后遍历这个数据，为每个请假请求创建一个表格行，并将其添加到表格的主体部分
 * 最后，它会更新页面上的表格，显示所有的请假请求
 */
async function fetchLeaveRequests() {
    // 向服务器发送请求，获取请假请求列表
    const response = await fetch('view-leave-requests');
    // 解析服务器返回的 JSON 数据
    const data = await response.json();
    // 获取页面上的表格主体部分
    const tableBody = document.getElementById('leave-requests-tbody');
    // 清空表格主体部分的内容
    tableBody.innerHTML = '';
    // 遍历请假请求列表
    data.forEach(request => {
        // 为每个请假请求创建一个表格行
        tableBody.innerHTML += `
                    <tr>
                        <td>${request.id}</td>
                        <td>${request.reason}</td>
                        <td>${request.start_date}</td>
                        <td>${request.end_date}</td>
                        <td>${request.status}</td>
                        <td>${request.created_at}</td>
                    </tr>
                `;
    });
}


/**
 * 异步函数，用于提交请假请求
 * 该函数会获取用户在表单中输入的请假原因、开始日期、结束日期和证明文件
 * 然后将这些数据打包成一个 FormData 对象，并发送到服务器端的 submit-leave-request 路由
 * 服务器端会根据提交的数据进行相应的处理，并返回处理结果
 * 函数会根据服务器返回的结果显示相应的提示信息，并在提交成功时刷新请假请求列表
 */
async function submitLeaveRequest() {
    // 获取用户输入的请假原因
    const reason = document.getElementById('reason').value;
    // 获取用户输入的开始日期
    const startDate = document.getElementById('start-date').value;
    // 获取用户输入的结束日期
    const endDate = document.getElementById('end-date').value;
    // 获取用户上传的证明文件
    const evidence = document.getElementById('evidence').files[0];

    // 创建一个 FormData 对象，用于封装表单数据
    const formData = new FormData();
    // 将请假原因添加到 FormData 对象中
    formData.append('reason', reason);
    // 将开始日期添加到 FormData 对象中
    formData.append('start_date', startDate);
    // 将结束日期添加到 FormData 对象中
    formData.append('end_date', endDate);
    // 如果用户上传了证明文件，则将其添加到 FormData 对象中
    if (evidence) {
        formData.append('evidence', evidence);
    }

    // 发起 AJAX 请求，将 FormData 对象发送到服务器端
    const response = await fetch('submit-leave-request', {
        // 请求方法为 POST
        method: 'POST',
        // 指定编码格式为 multipart/form-data
        encoding: 'multipart/form-data',
        // 请求体为 FormData 对象
        body: formData,
    });

    // 解析服务器返回的 JSON 数据
    const result = await response.json();
    // 在页面上显示服务器返回的提示信息
    alert(result.message);
    // 如果提交成功，则刷新请假请求列表
    if (result.success) fetchLeaveRequests();
}



window.onload = fetchLeaveRequests;