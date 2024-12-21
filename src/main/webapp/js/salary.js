// 获取教练数据
async function fetchCoaches() {
    const response = await fetch('get-coaches');
    const data = await response.json();
    const tbody = document.getElementById('coaches-tbody');
    tbody.innerHTML = '';
    data.forEach(coach => {
        tbody.innerHTML += `
        <tr>
          <td>${coach.id}</td>
          <td>${coach.name}</td>
          <td><input type="text" id="bankcard-${coach.id}" placeholder="请输入银行卡号"></td>
          <td><input type="number" id="base-salary-${coach.id}" placeholder="请输入基础工资" step="0.01" oninput="updateTotal(${coach.id})"></td>
          <td><input type="number" id="bonus-${coach.id}" placeholder="请输入奖金" step="0.01" oninput="updateTotal(${coach.id})"></td>
          <td><span id="total-${coach.id}">0.00</span></td>
          <td>
            <button onclick="paySalary(${coach.id})">发放工资</button>
            <button class="cancel-button" onclick="cancelSalary(${coach.id})">取消</button>
          </td>
        </tr>
      `;
    });
}

// 更新总金额
function updateTotal(coachId) {
    const baseSalary = parseFloat(document.getElementById(`base-salary-${coachId}`).value) || 0;
    const bonus = parseFloat(document.getElementById(`bonus-${coachId}`).value) || 0;
    const total = (baseSalary + bonus).toFixed(2);
    document.getElementById(`total-${coachId}`).textContent = total;
}

// 发放工资
async function paySalary(coachId) {
    const bankCard = document.getElementById(`bankcard-${coachId}`).value;
    const baseSalary = parseFloat(document.getElementById(`base-salary-${coachId}`).value) || 0;
    const bonus = parseFloat(document.getElementById(`bonus-${coachId}`).value) || 0;
    const amount = baseSalary + bonus;
    const note = prompt('请输入备注信息（可选）：') || '';

    const response = await fetch('pay-salary', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `coach_id=${coachId}&amount=${amount}&bankcard=${bankCard}&note=${encodeURIComponent(note)}`
    });

    const result = await response.json();
    alert(result.message);
    if (result.success) {
        fetchCoaches();
        fetchSalaryRecords();  // 更新工资记录表
    }
}

// 取消发放工资（触发发放请求）
async function cancelSalary(coachId) {
    const bankCard = document.getElementById(`bankcard-${coachId}`).value;
    const baseSalary = parseFloat(document.getElementById(`base-salary-${coachId}`).value) || 0;
    const bonus = parseFloat(document.getElementById(`bonus-${coachId}`).value) || 0;
    const amount = baseSalary + bonus;
    const note = '取消发放工资'; // 默认取消备注

    const response = await fetch('pay-salary', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `coach_id=${coachId}&amount=${amount}&bankcard=${bankCard}&note=${encodeURIComponent(note)}`
    });

    const result = await response.json();
    alert(result.message);
    if (result.success) {
        fetchCoaches();
        fetchSalaryRecords();  // 更新工资记录表
    }
}

// 获取所有工资发放记录
async function fetchSalaryRecords() {
    const response = await fetch('get-salary-records');
    const records = await response.json();
    const tbody = document.getElementById('salary-records-tbody');
    tbody.innerHTML = '';

    records.forEach(record => {
        tbody.innerHTML += `
        <tr>
          <td>${record.coach_name}</td>
          <td>${record.amount}</td>
          <td>${record.payment_date}</td>
          <td>${record.note}</td>
        </tr>
      `;
    });
}

window.onload = async () => {
    await fetchCoaches();         // 获取教练信息
    await fetchSalaryRecords();   // 获取所有工资发放记录
};