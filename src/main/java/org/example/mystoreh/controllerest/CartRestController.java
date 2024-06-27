package org.example.mystoreh.controllerest;

import jakarta.servlet.http.HttpSession;
import org.example.mystoreh.entity.Item;
import org.example.mystoreh.entity.Product;
import org.example.mystoreh.service.ItemService;
import org.example.mystoreh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/cart")
public class CartRestController {
    private ProductService productService;
    private ItemService itemService;

    @Autowired
    public CartRestController(ProductService productService, ItemService itemService) {
        this.productService = productService;
        this.itemService = itemService;
    }

    @GetMapping("/add")
    public ResponseEntity<Map<String, Object>> addToCart(
            @RequestParam Long productId,
            @RequestParam int quantity,
            HttpSession session
    ) {
        return addToCartProcess(productId, quantity, session);
    }

    private ResponseEntity<Map<String, Object>> addToCartProcess(Long productId, int quantityNumber, HttpSession session) {
        Product product = productService.findProductById(productId);
        if (product != null) {
            Item item = Item.builder()
                    .id(product.getId())
                    .name(product.getProductName())
                    .image(product.getProductImage())
                    .price(product.getProductPrice())
                    .quantity(quantityNumber)
                    .amount(product.getProductPrice() * quantityNumber)
                    .build();
            List<Item> cart = (List<Item>) session.getAttribute("cart");
            if (cart == null) {
                cart = new ArrayList<>();
                cart.add(item);
//                session.setAttribute("cart", cart);
            } else {
                boolean isExist = false;
                for (Item i : cart) {
                    if (i.getId().equals(item.getId())) {
                        i.setQuantity(i.getQuantity() + quantityNumber);
                        i.setAmount(i.getQuantity() * i.getPrice());
                        isExist = true;
                    }
                }
                if (!isExist) {
                    cart.add(item);
                }
            }
            // Tính toán số lượng còn lại của sản phẩm
            int remainingStock = product.getProductQuantity() - quantityNumber;
            // Cập nhật lại số lượng sản phẩm còn lại trong session
            session.setAttribute("remainingStock", remainingStock);

            session.setAttribute("cart", cart);
//            product.setProductQuantity(product.getProductQuantity() - quantityNumber);
//            productService.save(product);
            // Cập nhật số lượng sản phẩm có sẵn - không lưu vào DB ngay
            int count = itemService.countTotalProductQuantity(cart);
            session.setAttribute("totalProductQuantity", count);
            session.setAttribute("totalAmountOfCart", itemService.totalProductAmount(cart));
            Map<String, Object> response = new HashMap<>();
            response.put("cart", cart);
            response.put("totalProductQuantity", count);
            response.put("remainingStock", remainingStock);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    private void updateProductStock(Product product, int quantityNumber) {
        int currentStock = product.getProductQuantity();
        product.setProductQuantity(currentStock - quantityNumber);
        // Không lưu vào DB ngay mà để khi người dùng thực sự thanh toán
    }

//    @GetMapping("/cart")
//    public String showCart(Model model, HttpSession session) {
//        List<Item> cartItems = (List<Item>) session.getAttribute("cart");
//        model.addAttribute("cartItems", cartItems);
//        model.addAttribute("totalProductQuantity", getTotalProductQuantity(cartItems));
//        return "cart";
//    }
//
//    private int getTotalProductQuantity(List<Item> cartItems) {
//        return cartItems.stream()
//                .mapToInt(Item::getQuantity)
//                .sum();
//    }


}
