/**
 * 加载预约列表
 * 该函数会从服务器获取预约列表的 JSON 数据，并将其显示在页面上的表格中
 * 如果没有预约，则显示相应的提示信息
 */
function loadAppointments() {
    // 向服务器发送请求，获取预约列表
    fetch("student-appointments")
        // 解析服务器返回的 JSON 数据
        .then(response => response.json())
        // 遍历预约列表，将每个预约的信息添加到表格中
        .then(data => {
            const list = document.getElementById("appointment-list");
            list.innerHTML = "";
            if (data.length === 0) {
                // 如果没有预约，则显示提示信息
                list.innerHTML = "<tr><td colspan='3'>暂无预约</td></tr>";
            } else {
                // 遍历预约列表
                data.forEach(appt => {
                    // 将每个预约的信息添加到表格中
                    list.innerHTML += `
                <tr>
                  <td>${appt.date}</td>
                  <td>${appt.time}</td>
                  <td>
                    <button class="btn btn-danger" onclick="cancelAppointment(${appt.id})">取消</button>
                  </td>
                </tr>`;
                });
            }
        });
}

/**
 * 加载可预约时间段列表
 * 该函数会从服务器获取可预约时间段的 JSON 数据，并将其显示在页面上的表格中
 * 如果没有可预约时间段，则显示相应的提示信息
 */
function loadSchedules() {
    // 向服务器发送请求，获取可预约时间段列表
    fetch("available-schedules")
        // 解析服务器返回的 JSON 数据
        .then(response => response.json())
        // 遍历时间段列表，将每个时间段的信息添加到表格中
        .then(data => {
            const list = document.getElementById("schedule-list");
            list.innerHTML = "";
            if (data.length === 0) {
                // 如果没有可预约时间段，则显示提示信息
                list.innerHTML = "<tr><td colspan='4'>暂无可预约时间段</td></tr>";
            } else {
                // 遍历可预约时间段列表
                data.forEach(schedule => {
                    // 将每个时间段的信息添加到表格中
                    list.innerHTML += `
                <tr>
                  <td>${schedule.date}</td>
                  <td>${schedule.time}</td>
                  <td>${schedule.remaining}</td>
                  <td>
                    <button class="btn btn-primary" onclick="bookSchedule(${schedule.id})">预约</button>
                  </td>
                </tr>`;
                });
            }
        });
}


/**
 * 预约时间段
 * @param {number} scheduleId - 要预约的时间段的唯一标识符
 * 该函数会向服务器发送一个 POST 请求，请求预约指定的时间段
 * 如果服务器返回成功，则刷新页面以更新预约列表和可预约时间段列表
 */
function bookSchedule(scheduleId) {
    // 向服务器发送 POST 请求，请求预约指定的时间段
    fetch("book-schedule", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `schedule_id=${scheduleId}`
    })
        // 解析服务器返回的 JSON 数据
        .then(response => response.json())
        // 如果服务器返回成功，则提示预约成功并刷新页面
        .then(data => {
            if (data.success) {
                alert("预约成功！");
                loadAppointments();
                loadSchedules();
            } else {
                // 如果服务器返回失败，则提示预约失败
                alert("预约失败，请重试！");
            }
        });
}


/**
 * 取消预约
 * @param {number} appointmentId - 要取消的预约的唯一标识符
 * 该函数会向服务器发送一个 DELETE 请求，请求取消指定的预约
 * 如果服务器返回成功，则刷新页面以更新预约列表和可预约时间段列表
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
                loadSchedules();
            } else {
                // 如果服务器返回失败，则提示取消失败
                alert("取消失败，请重试！");
            }
        });
}

// 初始加载
loadAppointments();
loadSchedules();