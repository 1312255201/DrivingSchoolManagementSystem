function loadexams() {
    fetch('get-available-exams')
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('available-exams').querySelector('tbody');
            tbody.innerHTML = '';
            if (data.exams.length === 0) {
                tbody.innerHTML = '<tr><td colspan="6">暂无可选课程</td></tr>';
            } else {
                data.exams.forEach(exam => {
                    const tr = document.createElement('tr');
                    tr.innerHTML = `
              <td>${exam.exam_number}</td>
              <td>${exam.name}</td>
              <td>${exam.start_time}</td>
              <td>${exam.end_time}</td>
              <td>${exam.remaining_capacity}</td>
              <td><button class="btn btn-select" onclick="selectexam(${exam.id})">选课</button></td>
            `;
                    tbody.appendChild(tr);
                });
            }
        });
}

function selectexam(examId) {
    fetch('select-exam', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `exam_id=${examId}`
    })
        .then(response => response.json())
        .then(data => {
            alert(data.message);
            loadexams();
            loadSelectedexams();
        });
}



function loadSelectedexams() {
    fetch('get-selected-exams')
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('selected-exams').querySelector('tbody');
            tbody.innerHTML = '';
            if (data.exams.length === 0) {
                tbody.innerHTML = '<tr><td colspan="5">暂无已选课程</td></tr>';
            } else {
                data.exams.forEach(exam => {
                    const tr = document.createElement('tr');
                    tr.innerHTML = `
              <td>${exam.exam_number}</td>
              <td>${exam.name}</td>
              <td>${exam.start_time}</td>
              <td>${exam.end_time}</td>
              <td><button class="btn btn-cancel" onclick="cancelexam(${exam.id})">取消</button></td>
            `;
                    tbody.appendChild(tr);
                });
            }
        });
}


function cancelexam(examId) {
    fetch('cancel-exam', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `exam_id=${examId}`
    })
        .then(response => response.json())
        .then(data => {
            alert(data.message);
            loadexams();
            loadSelectedexams();
        });
}

// 初始化加载
loadexams();
loadSelectedexams();