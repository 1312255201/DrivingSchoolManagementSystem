function openEditModal(id, name, idnumber, phonenumber, email, role) {
    document.getElementById('userId').value = id;
    document.getElementById('userName').value = name;
    document.getElementById('userIdNumber').value = idnumber;
    document.getElementById('userPhoneNumber').value = phonenumber;
    document.getElementById('userEmail').value = email;
    document.getElementById('userRole').value = role;
    document.getElementById('editModal').style.display = 'block';
}

function closeEditModal() {
    document.getElementById('editModal').style.display = 'none';
}

function filterTable() {
    const searchInput = document.getElementById('searchInput').value.toLowerCase();
    const roleFilter = document.getElementById('roleFilter').value.toLowerCase();
    const startDate = document.getElementById('startDate').value;
    const endDate = document.getElementById('endDate').value;

    const table = document.getElementById('userTable');
    const rows = table.getElementsByTagName('tr');

    for (let i = 1; i < rows.length; i++) {
        const cells = rows[i].getElementsByTagName('td');
        const name = cells[1].textContent.toLowerCase();
        const idnumber = cells[2].textContent.toLowerCase();
        const phonenumber = cells[3].textContent.toLowerCase();
        const email = cells[4].textContent.toLowerCase();
        const role = cells[5].textContent.toLowerCase();
        const createTime = cells[6].textContent;

        let show = true;

        if (searchInput && ![name, idnumber, phonenumber, email].some(field => field.includes(searchInput))) {
            show = false;
        }

        if (roleFilter && role !== roleFilter) {
            show = false;
        }

        if (startDate && new Date(createTime) < new Date(startDate)) {
            show = false;
        }

        if (endDate && new Date(createTime) > new Date(endDate)) {
            show = false;
        }

        rows[i].style.display = show ? '' : 'none';
    }
}

function confirmDelete(userId) {
    const adminPassword = prompt("请输入管理员密码以确认删除操作：");

    if (adminPassword === null || adminPassword.trim() === "") {
        alert("管理员密码不能为空！");
        return;
    }

    if (confirm("确认要删除该用户吗？")) {
        // 使用 AJAX 发送删除请求
        fetch("DeleteUserServlet", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: `id=${userId}&adminPassword=${encodeURIComponent(adminPassword)}`
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert(data.message);
                    location.reload(); // 刷新页面以更新用户列表
                } else {
                    alert(data.message);
                }
            })
            .catch(error => {
                console.error("删除失败：", error);
                alert("服务器错误，请稍后重试！");
            });
    }
}

function closeDeleteModal() {
    document.getElementById('deleteModal').style.display = 'none';
}