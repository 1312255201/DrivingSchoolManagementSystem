// 打开模态框
function openModal(field, value) {
    document.getElementById('editModal').style.display = 'flex';
    document.getElementById('modalTitle').textContent = `编辑 ${field}`;
    document.getElementById('field').value = field;
    document.getElementById('newValue').value = value || '';
}

// 关闭模态框
function closeModal() {
    document.getElementById('editModal').style.display = 'none';
}

// 提交修改
function submitEdit() {
    const field = document.getElementById('field').value;
    const newValue = document.getElementById('newValue').value;

    // 发起 AJAX 请求
    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'EditProfileServlet', true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            if (xhr.responseText === 'success') {
                alert('修改成功！');
                document.getElementById(`display-${field}`).innerText = newValue;
                closeModal();
            } else {
                alert('修改失败，请重试！');
            }
        }
    };
    xhr.send(`field=${encodeURIComponent(field)}&newValue=${encodeURIComponent(newValue)}`);
}

// 点击模态框外部关闭
window.onclick = function (event) {
    const modal = document.getElementById('editModal');
    if (event.target === modal) {
        closeModal();
    }
};