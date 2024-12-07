function loadCourses() {
    fetch('get-available-courses')
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('available-courses').querySelector('tbody');
            tbody.innerHTML = '';
            if (data.courses.length === 0) {
                tbody.innerHTML = '<tr><td colspan="6">暂无可选课程</td></tr>';
            } else {
                data.courses.forEach(course => {
                    const tr = document.createElement('tr');
                    tr.innerHTML = `
              <td>${course.course_number}</td>
              <td>${course.name}</td>
              <td>${course.start_time}</td>
              <td>${course.end_time}</td>
              <td>${course.remaining_capacity}</td>
              <td><button class="btn btn-select" onclick="selectCourse(${course.id})">选课</button></td>
            `;
                    tbody.appendChild(tr);
                });
            }
        });
}

function loadSelectedCourses() {
    fetch('get-selected-courses')
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('selected-courses').querySelector('tbody');
            tbody.innerHTML = '';
            if (data.courses.length === 0) {
                tbody.innerHTML = '<tr><td colspan="5">暂无已选课程</td></tr>';
            } else {
                data.courses.forEach(course => {
                    const tr = document.createElement('tr');
                    tr.innerHTML = `
              <td>${course.course_number}</td>
              <td>${course.name}</td>
              <td>${course.start_time}</td>
              <td>${course.end_time}</td>
              <td><button class="btn btn-cancel" onclick="cancelCourse(${course.id})">取消</button></td>
            `;
                    tbody.appendChild(tr);
                });
            }
        });
}

function selectCourse(courseId) {
    fetch('select-course', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `course_id=${courseId}`
    })
        .then(response => response.json())
        .then(data => {
            alert(data.message);
            loadCourses();
            loadSelectedCourses();
        });
}

function cancelCourse(courseId) {
    fetch('cancel-course', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `course_id=${courseId}`
    })
        .then(response => response.json())
        .then(data => {
            alert(data.message);
            loadCourses();
            loadSelectedCourses();
        });
}

// 初始化加载
loadCourses();
loadSelectedCourses();