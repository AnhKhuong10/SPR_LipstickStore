function confirmDeleteItemInCart(itemId) {
    document.getElementById('confirm-modal-content').innerText = "Xác nhận xóa sản phẩm này khỏi giỏ hàng";
    document.getElementById('btn-confirm-modal').href = window.location.origin + "/checkout/item/delete/" + itemId;
    openModal('confirmModal');
}