/**
 * 加载教练已设置的时间段
 */
function loadSchedules() {
    const scheduleList = document.getElementById("schedule-list");
    scheduleList.innerHTML = `<p class="loading">加载中...</p>`;

    fetch('coach-schedules')
        .then(response => response.json())
        .then(data => {
            scheduleList.innerHTML = "";
            if (data.length === 0) {
                scheduleList.innerHTML = `<p class="empty-message">暂无已设置的时间段。</p>`;
            } else {
                data.forEach(item => {
                    const div = document.createElement("div");
                    div.className = "schedule-item";
                    div.innerHTML = `
                                <p><strong>日期:</strong> `+item.date+`</p>
                                <p><strong>时间段:</strong> `+item.time+`</p>
                                <button class="btn" onclick="removeSchedule(`+item.id+`)">删除</button>
                            `;
                    scheduleList.appendChild(div);
                });
            }
        })
        .catch(error => {
            console.error("加载失败:", error);
            scheduleList.innerHTML = `<p class="empty-message">加载失败，请稍后重试。</p>`;
        });
}

/**
 * 添加时间段
 */
function addSchedule() {
    const date = document.getElementById("date").value;
    const time = document.getElementById("time").value;

    if (!date || !time) {
        alert("请选择日期和时间段！");
        return;
    }

    fetch('add-schedule', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: 'date=' + date + '&time=' + time
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert("添加成功！");
                loadSchedules();
            } else {
                alert("添加失败，请重试！");
            }
        })
        .catch(error => {
            console.error("添加失败:", error);
            alert("添加失败，请检查网络或稍后重试！");
        });
}

/**
 * 删除时间段
 */
function removeSchedule(scheduleId) {
    if (confirm("确定要删除此时间段吗？")) {
        fetch(`remove-schedule?schedule_id=`+scheduleId, { method: 'DELETE' })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert("删除成功！");
                    loadSchedules();
                } else {
                    alert("删除失败，请重试！");
                }
            })
            .catch(error => {
                console.error("删除失败:", error);
                alert("删除失败，请稍后重试！");
            });
    }
}

/**
 * 加载学员预约情况
 */
function loadAppointments() {
    const appointmentList = document.getElementById("appointment-list");
    appointmentList.innerHTML = `<p class="loading">加载中...</p>`;

    fetch('coach-appointments')
        .then(response => response.json())
        .then(data => {
            appointmentList.innerHTML = "";
            if (data.length === 0) {
                appointmentList.innerHTML = `<p class="empty-message">暂无学员预约。</p>`;
            } else {
                data.forEach(item => {
                    const div = document.createElement("div");
                    div.className = "appointment-item";
                    div.innerHTML = `
                                <p><strong>学员姓名:</strong> ${item.student_name}</p>
                                <p><strong>预约时间:</strong> ${item.date} ${item.time}</p>
                                <button class="btn" onclick="cancelAppointment(`+item.id+`)">取消预约</button>
                            `;
                    appointmentList.appendChild(div);
                });
            }
        })
        .catch(error => {
            console.error("加载失败:", error);
            appointmentList.innerHTML = `<p class="empty-message">加载失败，请稍后重试。</p>`;
        });
}

/**
 * 取消预约
 */
function cancelAppointment(appointmentId) {
    if (confirm("确定要取消此预约吗？")) {
        fetch(`cancel-appointment?appointment_id=`+appointmentId, { method: 'DELETE' })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert("取消成功！");
                    loadAppointments();
                } else {
                    alert("取消失败，请重试！");
                }
            })
            .catch(error => {
                console.error("取消失败:", error);
                alert("取消失败，请稍后重试！");
            });
    }
}

// 初始加载
loadSchedules();
loadAppointments();