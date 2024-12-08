// 打开模态框
function openModal(field, value) {
    document.getElementById('editModal').style.display = 'flex';
    document.getElementById('modalTitle').textContent = `编辑 ${field}`;
    document.getElementById('field').value = field;
    document.getElementById('newValue').value = value || '';

    // 如果是敏感字段，需要二次确认
    const confirmationSection = document.getElementById('confirmationSection');
    if (field === 'password' || field === 'phonenumber') {
        confirmationSection.style.display = 'block';
        document.getElementById('confirmValue').value = ''; // 清空确认输入框
    } else {
        confirmationSection.style.display = 'none';
    }
}

// 关闭模态框
function closeModal() {
    document.getElementById('editModal').style.display = 'none';
}

// 提交修改
function submitEdit() {
    const field = document.getElementById('field').value;
    const newValue = document.getElementById('newValue').value;
    const confirmValue = document.getElementById('confirmValue') ? document.getElementById('confirmValue').value : '';

    // 检查是否需要二次确认
    if ((field === 'password' || field === 'phonenumber') && !confirmValue) {
        alert('请填写确认信息！');
        return;
    }

    // 发起 AJAX 请求
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'EditProfileServlet', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            switch (xhr.responseText) {
                case 'success':
                    alert('修改成功！');
                    if (field !== "password") {
                        document.getElementById(`display-${field}`).innerText = newValue;
                    }
                    closeModal();
                    break;

                case 'confirm-fail':
                    alert('确认失败，请重新输入密码！');
                    break;

                case 'fail':
                    alert('修改失败，请重试！');
                    break;

                case 'invalid-field':
                    alert('无效的字段！');
                    break;

                default:
                    alert('未知错误，请联系管理员！');
            }
        }
    };

    xhr.send(`field=${encodeURIComponent(field)}&newValue=${encodeURIComponent(newValue)}&confirmValue=${encodeURIComponent(confirmValue)}`);
}


window.onclick = function (event) {
    const modal = document.getElementById('editModal');
    if (event.target === modal) {
        closeModal();
    }
};