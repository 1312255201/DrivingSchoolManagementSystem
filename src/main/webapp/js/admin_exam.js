function submitexam() {
    const formData = new FormData(document.getElementById('exam-form'));

    fetch('create-exam', {
        method: 'POST',
        body: new URLSearchParams(formData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('考试创建成功！');
                document.getElementById('exam-form').reset();
                loadExams(); // 重新加载考试列表
            } else {
                alert('考试创建失败：' + data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('提交失败，请稍后再试！');
        });
}

function loadExams() {
    fetch('get-exams')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('exam-table').querySelector('tbody');
            tableBody.innerHTML = '';

            data.exams.forEach(exam => {
                const row = document.createElement('tr');
                row.innerHTML = `
              <td>${exam.exam_number}</td>
              <td>${exam.name}</td>
              <td>${exam.start_time} ~ ${exam.end_time}</td>
              <td>${exam.capacity}</td>
              <td>${exam.content}</td>
              <td><button onclick="deleteCourse(${exam.id})">删除</button></td>
            `;
                tableBody.appendChild(row);
            });

        })
        .catch(error => console.error('Error:', error));
}

function deleteCourse(examId) {
    if (confirm('确定要删除该课程吗？')) {
        fetch(`delete-exam?id=${examId}`, { method: 'DELETE' })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('删除成功！');
                    loadExams(); // 重新加载课程列表
                } else {
                    alert('删除失败：' + data.message);
                }
            })
            .catch(error => console.error('Error:', error));
    }
}
// 页面加载时加载课程数据
window.onload = loadExams;