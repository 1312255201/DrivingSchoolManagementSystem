/**
 * 加载教练已设置的时间段
 * 该函数会从服务器获取教练已设置的时间段的 JSON 数据，并将其显示在页面上的表格中
 * 如果没有已设置的时间段，则显示相应的提示信息
 */
function loadSchedules() {
    // 获取页面上用于显示时间段列表的元素
    const scheduleList = document.getElementById("schedule-list");
    // 在获取数据前，先显示一个加载中的提示
    scheduleList.innerHTML = `<p class="loading">加载中...</p>`;

    // 向服务器发送请求，获取教练已设置的时间段列表
    fetch('coach-schedules')
        // 解析服务器返回的 JSON 数据
        .then(response => response.json())
        // 处理获取到的数据
        .then(data => {
            // 清空时间段列表的内容
            scheduleList.innerHTML = "";
            // 如果没有时间段数据，显示提示信息
            if (data.length === 0) {
                scheduleList.innerHTML = `<p class="empty-message">暂无已设置的时间段。</p>`;
            } else {
                // 遍历时间段数据
                data.forEach(item => {
                    // 为每个时间段项创建一个 div 元素
                    const div = document.createElement("div");
                    // 设置 div 的 class 属性，用于样式控制
                    div.className = "schedule-item";
                    // 在 div 中添加时间段项的详细信息
                    div.innerHTML = `
                                <p><strong>日期:</strong> `+item.date+`</p>
                                <p><strong>时间段:</strong> `+item.time+`</p>
                                <button class="btn" onclick="removeSchedule(`+item.id+`)">删除</button>
                            `;
                    // 将 div 添加到时间段列表中
                    scheduleList.appendChild(div);
                });
            }
        })
        // 如果获取数据失败，记录错误并显示提示信息
        .catch(error => {
            console.error("加载失败:", error);
            scheduleList.innerHTML = `<p class="empty-message">加载失败，请稍后重试。</p>`;
        });
}


/**
 * 添加时间段
 * 该函数会获取用户在页面上选择的日期和时间段，并将其发送到服务器
 * 如果服务器返回成功，则刷新页面以更新时间段列表
 */
function addSchedule() {
    // 获取页面上日期选择框的值
    const date = document.getElementById("date").value;
    // 获取页面上时间段选择框的值
    const time = document.getElementById("time").value;

    // 如果日期或时间段未选择，则弹出提示信息
    if (!date ||!time) {
        alert("请选择日期和时间段！");
        return;
    }

    // 向服务器发送 POST 请求，请求添加新的时间段
    fetch('add-schedule', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: 'date=' + date + '&time=' + time
    })
        // 解析服务器返回的 JSON 数据
        .then(response => response.json())
        // 如果服务器返回成功，则提示添加成功并刷新页面
        .then(data => {
            if (data.success) {
                alert("添加成功！");
                loadSchedules();
            } else {
                // 如果服务器返回失败，则提示添加失败
                alert("添加失败，请重试！");
            }
        })
        // 如果获取数据失败，记录错误并显示提示信息
        .catch(error => {
            console.error("添加失败:", error);
            alert("添加失败，请检查网络或稍后重试！");
        });
}

/**
 * 删除时间段
 * @param {number} scheduleId - 要删除的时间段的唯一标识符
 * 该函数会向服务器发送一个 DELETE 请求，请求删除指定的时间段
 * 如果服务器返回成功，则刷新页面以更新时间段列表
 */
function removeSchedule(scheduleId) {
    // 弹出确认对话框，询问用户是否确定要删除此时间段
    if (confirm("确定要删除此时间段吗？")) {
        // 向服务器发送 DELETE 请求，请求删除指定的时间段
        fetch(`remove-schedule?schedule_id=`+scheduleId, { method: 'DELETE' })
            // 解析服务器返回的 JSON 数据
            .then(response => response.json())
            // 如果服务器返回成功，则提示删除成功并刷新页面
            .then(data => {
                if (data.success) {
                    alert("删除成功！");
                    loadSchedules();
                } else {
                    // 如果服务器返回失败，则提示删除失败
                    alert("删除失败，请重试！");
                }
            })
            // 如果获取数据失败，记录错误并显示提示信息
            .catch(error => {
                console.error("删除失败:", error);
                alert("删除失败，请稍后重试！");
            });
    }
}


/**
 * 加载学员预约情况
 * 该函数会从服务器获取学员预约列表的 JSON 数据，并将其显示在页面上的表格中
 * 如果没有学员预约，则显示相应的提示信息
 */
function loadAppointments() {
    // 获取页面上用于显示预约列表的元素
    const appointmentList = document.getElementById("appointment-list");
    // 在获取数据前，先显示一个加载中的提示
    appointmentList.innerHTML = `<p class="loading">加载中...</p>`;

    // 向服务器发送请求，获取学员预约列表
    fetch('coach-appointments')
        // 解析服务器返回的 JSON 数据
        .then(response => response.json())
        // 处理获取到的数据
        .then(data => {
            // 清空预约列表的内容
            appointmentList.innerHTML = "";
            // 如果没有预约数据，显示提示信息
            if (data.length === 0) {
                appointmentList.innerHTML = `<p class="empty-message">暂无学员预约。</p>`;
            } else {
                // 遍历预约数据
                data.forEach(item => {
                    // 为每个预约项创建一个 div 元素
                    const div = document.createElement("div");
                    // 设置 div 的 class 属性，用于样式控制
                    div.className = "appointment-item";
                    // 在 div 中添加预约项的详细信息
                    div.innerHTML = `
                                <p><strong>学员姓名:</strong> ${item.student_name}</p>
                                <p><strong>预约时间:</strong> ${item.date} ${item.time}</p>
                                <button class="btn" onclick="cancelAppointment(${item.id})">取消预约</button>
                            `;
                    // 将 div 添加到预约列表中
                    appointmentList.appendChild(div);
                });
            }
        })
        // 如果获取数据失败，记录错误并显示提示信息
        .catch(error => {
            console.error("加载失败:", error);
            appointmentList.innerHTML = `<p class="empty-message">加载失败，请稍后重试。</p>`;
        });
}

/**
 * 取消预约
 * @param {number} appointmentId - 要取消的预约的唯一标识符
 * 该函数会向服务器发送一个 DELETE 请求，请求取消指定的预约
 * 如果服务器返回成功，则刷新页面以更新预约列表
 */
function cancelAppointment(appointmentId) {
    // 向服务器发送 DELETE 请求，请求取消指定的预约
    fetch(`cancel-appointment?appointment_id=${appointmentId}`, {
        method: "DELETE"
    })
        // 解析服务器返回的 JSON 数据
        .then(response => response.json())
        // 如果服务器返回成功，则提示取消成功并刷新页面
        .then(data => {
            if (data.success) {
                alert("取消成功！");
                loadAppointments();
            } else {
                // 如果服务器返回失败，则提示取消失败
                alert("取消失败，请重试！");
            }
        });
}


// 初始加载
loadSchedules();
loadAppointments();