package org.example.mystoreh.controller;

import jakarta.servlet.http.HttpSession;
import org.example.mystoreh.entity.Item;
import org.example.mystoreh.entity.Product;
import org.example.mystoreh.exception.ProductNotFoundException;
import org.example.mystoreh.service.ItemService;
import org.example.mystoreh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("cart")
public class CartController {
    private ProductService productService;
    private ItemService itemService;

    @Autowired
    public CartController(ProductService productService, ItemService itemService) {
        this.productService = productService;
        this.itemService = itemService;
    }

    @GetMapping("")
    public String goToCart(Model model, HttpSession session){
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        Map<Long, Integer> productStocks = getCartItemsAndStocks(cart);
        model.addAttribute("cart", cart);
        model.addAttribute("productStocks", productStocks);
        return "pages/cart";
    }

    @GetMapping("/item/delete/{itemId}")
    public String deleteItemInCart(@PathVariable Long itemId, Model model, HttpSession session){
        List<Item> items = (List<Item>)session.getAttribute("cart");
        items.removeIf(item -> item.getId().equals(itemId));

        // Cập nhật lại số lượng sản phẩm có sẵn khi xóa sản phẩm khỏi giỏ hàng
        //updateProductStockOnDelete(itemId, session);

        session.setAttribute("totalProductQuantity", itemService.countTotalProductQuantity(items));
        session.setAttribute("totalAmountOfCart", itemService.totalProductAmount(items));
        Map<Long, Integer> productStocks = getCartItemsAndStocks(items);
        model.addAttribute("cart", items);
        model.addAttribute("productStocks", productStocks);
        session.setAttribute("cart", items);
        return "pages/cart";
    }
    private Map<Long, Integer> getCartItemsAndStocks(List<Item> cart) {
        Map<Long, Integer> productStocks = new HashMap<>();
        for (Item item : cart) {
            Product product = productService.findProductById(item.getId());
            productStocks.put(item.getId(), product.getProductQuantity());
        }
        return productStocks;
    }

    private void updateProductStockOnDelete(Long itemId, HttpSession session) {
        List<Item> cartItems = (List<Item>) session.getAttribute("cart");
        Item deletedItem = cartItems.stream().filter(item -> item.getId().equals(itemId)).findFirst().orElse(null);
        if (deletedItem != null) {
            Product product = productService.findProductById(itemId);
            if (product != null) {
                int currentStock = product.getProductQuantity();
                int quantityInCart = deletedItem.getQuantity();
                product.setProductQuantity(currentStock + quantityInCart);
                // Không lưu vào DB ngay mà để khi người dùng thực sự cập nhật
            }
        }
    }
}
