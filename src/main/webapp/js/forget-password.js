async function submitRequest() {
    const email = document.getElementById('email').value;
    if (!email) {
        alert("请输入邮箱地址！");
        return;
    }

    const response = await fetch('forgot-password', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `email=${encodeURIComponent(email)}`
    });

    const result = await response.json();
    document.getElementById('message').innerText = result.message;
}