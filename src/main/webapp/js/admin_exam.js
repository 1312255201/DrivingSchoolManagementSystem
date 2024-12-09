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
                loadexams(); // 重新加载考试列表
            } else {
                alert('考试创建失败：' + data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('提交失败，请稍后再试！');
        });
}


// 页面加载时加载课程数据
window.onload = loadexams;