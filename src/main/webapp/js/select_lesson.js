function openEditModal(courseId){
    fetch('select-course', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: `course_id=${courseId}`
    })
        .then(response => response.json())
        .then(data => {
            alert(data.message);
            loadCourses();
            loadSelectedCourses();
        });
}
function filterTable1() {
    // 获取搜索框中的值，并转换为小写
    const searchInput = document.getElementById('searchInput').value.toLowerCase();

    // 获取表格元素
    const table = document.getElementById('userTable');
    // 获取表格中的所有行
    const rows = table.getElementsByTagName('tr');

    // 遍历表格中的每一行，从第二行开始（因为第一行通常是表头）
    for (let i = 1; i < rows.length; i++) {
        // 获取当前行的所有单元
        const cells = rows[i].getElementsByTagName('td');
        // 获取姓名单元格的文本内容，并转换为小写
        const name = cells[1].textContent.toLowerCase();
        // 获取身份证号单元格的文本内容，并转换为小写
        const idnumber = cells[2].textContent.toLowerCase();
        // 获取电话号码单元格的文本内容，并转换为小写

        // 获取电子邮件单元格的文本内容，并转换为小写


        // 初始设置当前行应该显示
        let show = true;

        // 如果搜索框中有值，并且姓名、身份证号、电话号码或电子邮件中没有包含搜索词，则隐藏该行
        if (searchInput && ![name, idnumber].some(field => field.includes(searchInput))) {
            show = false;
        }

        // 根据 show 变量的值来设置当前行的显示状态
        rows[i].style.display = show ? '' : 'none';
    }
}
