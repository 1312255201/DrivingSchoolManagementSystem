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
    const evidence = document.getElementById('evidence').files[0];

    const formData = new FormData();
    formData.append('reason', reason);
    formData.append('start_date', startDate);
    formData.append('end_date', endDate);
    if (evidence) {
        formData.append('evidence', evidence);
    }

    const response = await fetch('submit-leave-request', {
        method: 'POST',
        encoding: 'multipart/form-data',
        body: formData,
    });
    const result = await response.json();
    alert(result.message);
    if (result.success) fetchLeaveRequests();
}


window.onload = fetchLeaveRequests;