const minusBtn = document.querySelector('.minus');
const plusBtn = document.querySelector('.plus');
const quantityInput = document.querySelector('.quantity');
const quantitySock = document.getElementById('quantity-sock');
const productQuantity = parseInt(quantitySock.textContent);

minusBtn.addEventListener('click', () => {
    let currentQuantity = parseInt(quantityInput.value);
    if (currentQuantity > 1) {
        quantityInput.value = currentQuantity - 1;
    } else {
        document.getElementById("toast-error-content").innerText = "So luong khong the it hon 0";
        showToast("toast-error");
    }
});

plusBtn.addEventListener('click', () => {
    let currentQuantity = parseInt(quantityInput.value);
    //if (currentQuantity < parseInt("[[${product.productQuantity}]]")) {
    if (currentQuantity < productQuantity) {
        quantityInput.value = currentQuantity + 1;
    } else {
        document.getElementById("toast-error-content").innerText = "So luong khong the nhieu hon" + quantityInput.value;
        showToast("toast-error");
    }
});

function addToCartAPI() {
    fetch(window.location.origin + '/cart/test')
        .then(response => {
            // Check if response is successful
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // Parse response body as JSON
            return response.json();
        })
        .then(data => {
            // Work with JSON data
            console.log(data);
        })
        .catch(error => {
            // Handle errors
            console.error('There was a problem with the fetch operation:', error);
        });
}

function addToCart(productId) {
    const quantityIp = document.getElementById('quantity-Input').value;
    console.log(productId)
    console.log(quantityIp)

    fetch(window.location.origin + '/api/cart/add?productId=' + productId + '&quantity=' + quantityIp)
        .then(response => {
            // Check if response is successful
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            // Parse response body as JSON
            return response.json();
        })
        .then(data => {
            // Work with JSON data
            // Cập nhật số lượng sản phẩm còn lại
            // const currentQuantity = parseInt(document.getElementById("quantity-sock").innerText);
            // const newQuantity = currentQuantity - parseInt(quantityIp);
            // document.getElementById("quantity-sock").innerText = newQuantity;

            // Cập nhật số lượng sản phẩm có sẵn trên giao diện
            document.getElementById('quantity-sock').innerText = data.remainingStock;

            // document.getElementById("quantity-item-in-cart").innerText = data.totalProductQuantity;
            document.getElementById("quantity-item-in-cart").setAttribute('value', data.totalProductQuantity);
            // Cập nhật nội dung của modal
            updateCartModal(data.cart);
            console.log(data);
        })
        .catch(error => {
            // Handle errors
            console.error('There was a problem with the fetch operation:', error);
        });
}

function updateCartModal(cart) {
    // Lấy element của modal
    const cartModalHeader = document.querySelector('#exampleModal .modal-header');
    const cartModalBody = document.querySelector('#exampleModal .modal-body');

    const totalItems = cart.reduce((total, item) => total + item.quantity, 0);
    cartModalHeader.innerHTML = '';
    cartModalBody.innerHTML = '';

    // Nếu giỏ hàng không rỗng
    if (cart.length > 0) {
        // Tạo tiêu đề cho modal header
        const cartHeaderTitle = document.createElement('h5');
        cartHeaderTitle.textContent = `Giỏ hàng của bạn (${totalItems} sản phẩm)`;
        cartModalHeader.appendChild(cartHeaderTitle);

        // Tạo danh sách các sản phẩm trong giỏ hàng
        const cartList = document.createElement('ul');
        cart.forEach(item => {
            const listItem = document.createElement('li');
            const productName = item.name.length > 25 ? item.name.substring(0, 25) + '...' : item.name;
            listItem.textContent = `${productName} - ${item.quantity}`;
            cartList.appendChild(listItem);
        });
        // Thêm danh sách vào modal
        cartModalBody.appendChild(cartList);
    } else {
        // Nếu giỏ hàng rỗng, hiển thị thông báo
        cartModalBody.textContent = 'Giỏ hàng trống';
    }
}
