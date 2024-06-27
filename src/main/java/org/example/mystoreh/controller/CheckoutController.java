package org.example.mystoreh.controller;

import jakarta.servlet.http.HttpSession;
import org.example.mystoreh.entity.*;
import org.example.mystoreh.service.*;
import org.example.mystoreh.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    private UserService userService;
    private UserAddressService userAddressService;
    private ProductService productService;
    private ItemService itemService;
    private OrderService orderService;

    @Autowired
    public CheckoutController(UserService userService, UserAddressService userAddressService, ProductService productService, ItemService itemService, OrderService orderService) {
        this.userService = userService;
        this.userAddressService = userAddressService;
        this.productService = productService;
        this.itemService = itemService;
        this.orderService = orderService;
    }

    @GetMapping("")
    public String checkout(Authentication authentication, Model model) {
        getAuthentication(authentication, model);
        return "pages/checkout";
    }

    @GetMapping("/item/delete/{itemId}")
    public String deleteItemInCart(@PathVariable Long itemId, Model model, HttpSession session, Authentication authentication) {
        List<Item> items = (List<Item>) session.getAttribute("cart");
        items.removeIf(item -> item.getId().equals(itemId));
        session.setAttribute("totalProductQuantity", itemService.countTotalProductQuantity(items));
        session.setAttribute("totalAmountOfCart", itemService.totalProductAmount(items));
        Map<Long, Integer> productStocks = getCartItemsAndStocks(items);
        model.addAttribute("cart", items);
        model.addAttribute("productStocks", productStocks);
        session.setAttribute("cart", items);
        getAuthentication(authentication, model);
        return "pages/checkout";
    }

    @PostMapping("/set-address")
    public String setDefaultAddress(@RequestParam("addressId") Long addressId,
                                    RedirectAttributes redirectAttributes,
                                    Authentication authentication) {
        AuthUser currentUser = (AuthUser) authentication.getPrincipal();
        userAddressService.setDefaultAddress(addressId, currentUser.getId());
        System.out.println(currentUser);
        redirectAttributes.addFlashAttribute("successOke", "Đổi mặc định thành công");
        return "redirect:/checkout";
    }

    private Map<Long, Integer> getCartItemsAndStocks(List<Item> cart) {
        Map<Long, Integer> productStocks = new HashMap<>();
        for (Item item : cart) {
            Product product = productService.findProductById(item.getId());
            productStocks.put(item.getId(), product.getProductQuantity());
        }
        return productStocks;
    }

    private void getAuthentication(Authentication authentication, Model model) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        User user = userService.findUserByUsername(authUser.getUsername());

        UserAddress address = userAddressService.getUserAddressDefault(user.getUserAddressList());

        List<UserAddress> userAddresses = userAddressService.findByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("address", address);
        model.addAttribute("userAddresses", userAddresses);
    }
    //@RequestParam String orderNote,
    //@RequestParam Long userShippingAddressId,

    @GetMapping("/order")
    @Transactional
    public String order(@RequestParam String orderNote,
                        Authentication authentication,
                        RedirectAttributes redirectAttributes,
                        HttpSession session) {
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        User user = userService.findUserByUsername(authUser.getUsername());
        List<Item> items = (List<Item>) session.getAttribute("cart");
        Order order = new Order();
        order.setUser(user);
        order.setOrderCreateAt(DateTimeUtil.getCurrentDateTimeFormatted());
        order.setOrderShippedDate(DateTimeUtil.getCurrentDateTimeFormatted());
        order.setOrderStatus(new OrderStatus(1L, null, null));
        order.setOrderDiscount(0);
        order.setOrderTotalAmount(Double.parseDouble(session.getAttribute("totalAmountOfCart").toString()));
        //order.setOrderTotalAmount((Double) session.getAttribute("totalAmountOfCart"));
        order.setOrderNote(orderNote);

        UserAddress userAddress = getUserShippingAddress(user.getUserAddressList(), 1L);
        order.setReceiverName(userAddress.getReceiverName());
        order.setReceiverPhone(userAddress.getReceiverPhone());
        order.setShippingAddress(userAddress.getUserAddressName());
        order.setOrderDetailList(getOrderDetailList((List<Item>)session.getAttribute("cart"),order));
        orderService.save(order);

        session.removeAttribute("cart");
        session.removeAttribute("totalAmountOfCart");
        redirectAttributes.addFlashAttribute("successOke", "Đặt hàng thành công");
        return "redirect:/main";
    }

    private List<OrderDetail> getOrderDetailList(List<Item> items, Order order){
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for(Item item: items){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setProductId(item.getId());
            orderDetail.setProductName(item.getName());
            orderDetail.setProductImage(item.getImage());
            orderDetail.setProductPrice(item.getPrice());
            orderDetail.setQuantity(item.getQuantity());
            orderDetail.setAmount(item.getAmount());
            orderDetail.setOrder(order);
            orderDetailList.add(orderDetail);
        }
        return orderDetailList;
    }

    private UserAddress getUserShippingAddress(List<UserAddress> userAddressList, Long userShippingAddressId) {
        for (UserAddress userAddress : userAddressList) {
            if (Objects.equals(userAddress.getId(), userShippingAddressId)) {
                return userAddress;
            }
        }
        return null;
    }
}
