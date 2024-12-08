function loadStudents() {
    const filter = document.getElementById("filter").value;

    fetch(`my-students?filter=` + filter)
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById("student-table-body");
            tbody.innerHTML = ""; // 清空表格

            data.forEach(student => {
                const row = document.createElement("tr");

                row.innerHTML = `
            <td>${student.name}</td>
            <td>${student.phonenumber}</td>
            <td>${student.teachLevel}</td>
            <td>${student.state}</td>
            <td>${student.remark}</td>
          `;

                tbody.appendChild(row);
            });
        })
        .catch(err => console.error("数据加载失败", err));
}

// 页面加载时初始化
document.addEventListener("DOMContentLoaded", loadStudents);