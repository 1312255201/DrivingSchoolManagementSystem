/**
 * 加载学生列表
 * 该函数会从服务器获取学生列表的 JSON 数据，并根据指定的筛选条件将其显示在页面上的表格中
 * 如果没有指定筛选条件，则显示所有学生
 * 函数会在页面加载完成后自动调用
 */
function loadStudents() {
    // 获取筛选条件
    const filter = document.getElementById("filter").value;

    // 向服务器发送请求，获取学生列表
    fetch(`my-students?filter=` + filter)
        // 解析服务器返回的 JSON 数据
        .then(response => response.json())
        // 遍历学生列表，将每个学生的信息添加到表格中
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
        // 如果数据加载失败，显示错误信息
        .catch(err => console.error("数据加载失败", err));
}

// 页面加载时初始化
document.addEventListener("DOMContentLoaded", loadStudents);