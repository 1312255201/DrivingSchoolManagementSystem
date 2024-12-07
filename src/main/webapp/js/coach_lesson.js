function submitCourse() {
    const formData = new FormData(document.getElementById('course-form'));

    fetch('create-course', {
        method: 'POST',
        body: new URLSearchParams(formData)
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('课程创建成功！');
                document.getElementById('course-form').reset();
                loadCourses(); // 重新加载课程列表
            } else {
                alert('课程创建失败：' + data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('提交失败，请稍后再试！');
        });
}

function loadCourses() {
    fetch('get-my-courses')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.getElementById('course-table').querySelector('tbody');
            tableBody.innerHTML = '';

            if (data.success) {
                data.courses.forEach(course => {
                    const row = document.createElement('tr');
                    row.innerHTML = `
              <td>${course.course_number}</td>
              <td>${course.name}</td>
              <td>${course.start_time} ~ ${course.end_time}</td>
              <td>${course.capacity}</td>
              <td>${course.content}</td>
              <td><button onclick="deleteCourse(${course.id})">删除</button></td>
            `;
                    tableBody.appendChild(row);
                });
            } else {
                tableBody.innerHTML = '<tr><td colspan="6">暂无课程</td></tr>';
            }
        })
        .catch(error => console.error('Error:', error));
}

function deleteCourse(courseId) {
    if (confirm('确定要删除该课程吗？')) {
        fetch(`delete-course?id=${courseId}`, { method: 'DELETE' })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert('删除成功！');
                    loadCourses(); // 重新加载课程列表
                } else {
                    alert('删除失败：' + data.message);
                }
            })
            .catch(error => console.error('Error:', error));
    }
}

// 页面加载时加载课程数据
window.onload = loadCourses;