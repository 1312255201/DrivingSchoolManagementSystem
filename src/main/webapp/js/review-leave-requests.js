async function fetchLeaveRequests() {
    const response = await fetch('admin-view-leave-requests');
    const data = await response.json();
    const tableBody = document.getElementById('leave-requests-tbody');
    tableBody.innerHTML = '';
    data.forEach(request => {
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

function downloadProof(requestId) {
    window.location.href = `download-proof?id=${requestId}`;
}


async function reviewRequest(id, action) {
    const comments = prompt('请输入审核意见:');
    if (!comments) return alert('审核意见不能为空！');
    const response = await fetch('admin-review-leave-request', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `id=${id}&action=${action}&comments=${encodeURIComponent(comments)}`
    });
    const result = await response.json();
    alert(result.message);
    if (result.success) fetchLeaveRequests();
}

window.onload = fetchLeaveRequests;