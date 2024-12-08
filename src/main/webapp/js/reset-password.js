async function resetPassword() {
    const urlParams = new URLSearchParams(window.location.search);
    const token = urlParams.get('token');
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;

    if (!password || !confirmPassword) {
        alert("请输入密码！");
        return;
    }
    if (password !== confirmPassword) {
        alert("两次输入的密码不一致！");
        return;
    }

    const response = await fetch('reset-password', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `token=${encodeURIComponent(token)}&password=${encodeURIComponent(password)}`
    });

    const result = await response.json();
    document.getElementById('message').innerText = result.message;
}