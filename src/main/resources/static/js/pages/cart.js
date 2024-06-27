// const minusBtnCart = document.querySelector('.minus-in-cart');
// const plusBtnCart = document.querySelector('.plus-in-cart');
//const quantityInputInCart = document.querySelector('.quantity');
// const quantitySock = document.getElementById('quantity-sock-in-cart');
// const productQuantity = parseInt(quantitySock.textContent);
//
// minusBtnCart.addEventListener('click', () => {
//     let currentQuantity = parseInt(quantityInputInCart.value);
//     console.log(currentQuantity);
//     if (currentQuantity > 1) {
//         quantityInputInCart.value = currentQuantity - 1;
//     } else {
//         document.getElementById("toast-error-content").innerText = "So luong khong the it hon 0";
//         showToast("toast-error");
//     }
// });
//
// plusBtnCart.addEventListener('click', () => {
//     let currentQuantity = parseInt(quantityInputInCart.value);
//     console.log(currentQuantity);
//     if (currentQuantity < productQuantity) {
//         quantityInputInCart.value = currentQuantity + 1;
//     } else {
//         document.getElementById("toast-error-content").innerText = "So luong khong the nhieu hon" + quantityInputInCart.value;
//         showToast("toast-error");
//     }
// });

document.addEventListener("DOMContentLoaded", function () {
    const plusButtons = document.querySelectorAll('.plus-in-cart');
    const minusButtons = document.querySelectorAll('.minus-in-cart');
    const quantityInputs = document.querySelectorAll('#quantity-input-in-cart');
    const totalPriceElement = document.querySelector('.total-price');
    plusButtons.forEach(button => {
        button.addEventListener('click', updateCartTotals);
    });
    minusButtons.forEach(button => {
        button.addEventListener('click', updateCartTotals);
    });

    function updateCartTotals() {
        let totalAmount = 0;
        quantityInputs.forEach((input, index) => {
            const quantityElement = input;
            const priceElement = document.querySelectorAll('.price')[index];
            const totalProductElement = document.querySelectorAll('.total-product')[index];
            const stockElement = document.querySelectorAll('#quantity-sock-in-cart')[index];
            console.log(quantityElement);
            console.log(totalProductElement);
        });
    }

    document.querySelectorAll(".plus-in-cart").forEach(function (button) {
        button.addEventListener("click", function () {
            const input = this.parentNode.querySelector(".quantity");
            let currentQuantity = parseInt(input.value);
            const quantitySock = this.parentNode.querySelector('#quantity-sock-in-cart');
            const productQuantity = parseInt(quantitySock.textContent);
            if (currentQuantity < productQuantity) {
                input.value = parseInt(input.value) + 1;
            } else {
                document.getElementById("toast-error-content").innerText = "So luong khong the nhieu hon" + input.value;
                showToast("toast-error");
            }
        });
    });

    document.querySelectorAll(".minus-in-cart").forEach(function (button) {
        button.addEventListener("click", function () {
            const input = this.parentNode.querySelector(".quantity");
            let currentQuantity = parseInt(input.value);
            if (currentQuantity > 1) {
                input.value = currentQuantity- 1;
            }else {
                document.getElementById("toast-error-content").innerText = "So luong khong the it hon 1";
                showToast("toast-error");
            }
        });
    });
});

function confirmDeleteItemInCart(itemId) {
    document.getElementById('confirm-modal-content').innerText = "Xác nhận xóa sản phẩm này khỏi giỏ hàng";
    document.getElementById('btn-confirm-modal').href = window.location.origin + "/cart/item/delete/" + itemId;
    openModal('confirmModal');
}