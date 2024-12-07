async function fetchLeaveRequests() {
    const response = await fetch('view-leave-requests');
    const data = await response.json();
    const tableBody = document.getElementById('leave-requests-tbody');
    tableBody.innerHTML = '';
    data.forEach(request => {
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

async function submitLeaveRequest() {
    const reason = document.getElementById('reason').value;
    const startDate = document.getElementById('start-date').value;
    const endDate = document.getElementById('end-date').value;

    const response = await fetch('submit-leave-request', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `reason=${encodeURIComponent(reason)}&start_date=${startDate}&end_date=${endDate}`
    });
    const result = await response.json();
    alert(result.message);
    if (result.success) fetchLeaveRequests();
}

window.onload = fetchLeaveRequests;