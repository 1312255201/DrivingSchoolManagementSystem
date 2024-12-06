// 获取预约情况
function loadAppointments() {
    fetch("student-appointments")
        .then(response => response.json())
        .then(data => {
            const list = document.getElementById("appointment-list");
            list.innerHTML = "";
            if (data.length === 0) {
                list.innerHTML = "<tr><td colspan='3'>暂无预约</td></tr>";
            } else {
                data.forEach(appt => {
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

// 获取可预约时间段
function loadSchedules() {
    fetch("available-schedules")
        .then(response => response.json())
        .then(data => {
            const list = document.getElementById("schedule-list");
            list.innerHTML = "";
            if (data.length === 0) {
                list.innerHTML = "<tr><td colspan='4'>暂无可预约时间段</td></tr>";
            } else {
                data.forEach(schedule => {
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

// 预约时间段
function bookSchedule(scheduleId) {
    fetch("book-schedule", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `schedule_id=${scheduleId}`
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("预约成功！");
                loadAppointments();
                loadSchedules();
            } else {
                alert("预约失败，请重试！");
            }
        });
}

// 取消预约
function cancelAppointment(appointmentId) {
    fetch(`cancel-appointment?appointment_id=${appointmentId}`, {
        method: "DELETE"
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("取消成功！");
                loadAppointments();
                loadSchedules();
            } else {
                alert("取消失败，请重试！");
            }
        });
}

// 初始加载
loadAppointments();
loadSchedules();