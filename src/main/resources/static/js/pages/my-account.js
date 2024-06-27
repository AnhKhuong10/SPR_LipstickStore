// Lấy tất cả các thẻ <a> trong danh sách
const accountLinks = document.querySelectorAll('.account-settings-links a');

// Lấy tất cả các div chứa nội dung tương ứng
const accountContentPanes = document.querySelectorAll('.tab-content > .tab-pane');

// Lắng nghe sự kiện click trên các thẻ <a>
accountLinks.forEach((link, index) => {
    link.addEventListener('click', (event) => {
        event.preventDefault(); // Ngăn chặn hành vi mặc định của thẻ <a>

        // Ẩn tất cả các nội dung
        accountContentPanes.forEach(pane => {
            pane.classList.remove('active', 'show');
        });

        // Hiển thị nội dung tương ứng
        accountContentPanes[index].classList.add('active', 'show');
    });
});

