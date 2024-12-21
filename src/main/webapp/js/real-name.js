document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    const avatarInput = document.getElementById("avatar");
    const previewContainer = document.getElementById("image-preview");

    // 实时显示上传图片的预览
    avatarInput.addEventListener("change", function () {
        const file = avatarInput.files[0];
        previewContainer.innerHTML = ""; // 清空之前的预览
        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                const img = document.createElement("img");
                img.src = e.target.result;
                previewContainer.appendChild(img);
            };
            reader.readAsDataURL(file);
        }
    });

    // 使用 AJAX 提交表单
    form.addEventListener("submit", function (event) {
        event.preventDefault(); // 阻止默认提交

        const formData = new FormData(form); // 构建表单数据对象
        const xhr = new XMLHttpRequest();
        xhr.open("POST", "CompleteRealNameServlet", true); // 设置 POST 请求

        // 添加请求成功回调
        xhr.onload = function () {
            if (xhr.status === 200) {
                alert("实名信息提交成功！");
                window.location.href = "dashboard.jsp";
            }
            else{
                const response = JSON.parse(xhr.responseText);
                alert(response.error || "提交过程中发生错误，请检查网络或稍后再试。");
            }
        };

        // 添加请求失败回调
        xhr.onerror = function () {
            alert("提交过程中发生错误，请检查网络或稍后再试。");
        };

        // 发送表单数据
        xhr.send(formData);
    });
});
