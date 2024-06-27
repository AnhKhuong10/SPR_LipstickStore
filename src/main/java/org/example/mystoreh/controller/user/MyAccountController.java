package org.example.mystoreh.controller.user;

import jakarta.servlet.http.HttpServletRequest;
import org.example.mystoreh.dto.UpdateUserDetailDTO;
import org.example.mystoreh.entity.*;
import org.example.mystoreh.service.UserAddressService;
import org.example.mystoreh.service.UserDetailService;
import org.example.mystoreh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@SessionAttributes("user")
public class MyAccountController {
    private UserService userService;
    private UserDetailService userDetailService;
    private UserAddressService userAddressService;

    @Autowired
    public MyAccountController(UserService userService, UserDetailService userDetailService, UserAddressService userAddressService) {
        this.userService = userService;
        this.userDetailService = userDetailService;
        this.userAddressService = userAddressService;
    }

    @GetMapping("/my-account")
    @Transactional(propagation = Propagation.REQUIRED)
    public String showMyAccountPage(Model model, Principal principal, Authentication authentication) {
        String username = principal.getName();
        model.addAttribute("username", username);
        AuthUser user = (AuthUser)  authentication.getPrincipal();
        User myUser = userService.findUserByUsername(user.getUsername());
        model.addAttribute("user", user);
        model.addAttribute("myUser", myUser);
        List<Order> orderList = myUser.getOrderList();
        orderList.forEach(System.out::println);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for(Order order: orderList){
            orderDetailList.addAll(order.getOrderDetailList());
        }
        orderDetailList.forEach(System.out::println);
        List<UserAddress> userAddresses = userAddressService.findByUserId(user.getId());

        Optional<UserDetail> userDetail = userDetailService.getUserDetailByUserId(user.getId());
        if (userDetail.isPresent()) {
            UpdateUserDetailDTO update = UpdateUserDetailDTO.builder()
                    .mobile(userDetail.get().getMobile())
                    .gender(userDetail.get().getGender())
                    .email(userDetail.get().getEmail())
                    .password(user.getPassword())
                    .build();
            model.addAttribute("updateUser", update);
        }
        model.addAttribute("userAddresses", userAddresses);
        model.addAttribute("activeTab", "account-general");
        return "pages/my-account";
    }

//    @GetMapping("/my-account")
//    public String showMyAccountPage(Model model, @ModelAttribute("user") User user, HttpServletRequest request) {
//        // Lấy thông tin người dùng từ session
//        User currentUser = (User) request.getSession().getAttribute("loggedInUser");
//        populateModelWithUserDetails(currentUser, user, model);
//        List<UserAddress> userAddresses = userAddressService.findByUserId(currentUser.getId());
//        userAddresses.forEach(System.out::println);
//        model.addAttribute("userAddresses", userAddresses);
//        model.addAttribute("activeTab", "account-general");
//        return "pages/my-account";
//    }

//    @PostMapping("/editProfile")
//    public String editProfile(@ModelAttribute("updateUser") UpdateUserDetailDTO updateUserDetailDTO, HttpServletRequest request, RedirectAttributes redirectAttributes) {
//        User currentUser = (User) request.getSession().getAttribute("loggedInUser");
//        userDetailService.updateProfile(updateUserDetailDTO, currentUser.getId());
//        redirectAttributes.addFlashAttribute("successMessage", "Hồ sơ của bạn đã được cập nhật thành công.");
//        return "redirect:/my-account";
//    }

    @PostMapping("/editProfile")
    public String editProfile(@ModelAttribute("updateUser") UpdateUserDetailDTO updateUserDetailDTO, RedirectAttributes redirectAttributes) {
        // Lấy thông tin người dùng hiện tại từ Spring Security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthUser user = (AuthUser)  authentication.getPrincipal();
        System.out.println(updateUserDetailDTO);
        // Cập nhật hồ sơ người dùng
        userDetailService.updateProfile(updateUserDetailDTO, user.getId());

        // Thêm thông báo thành công vào RedirectAttributes
        redirectAttributes.addFlashAttribute("successMessage", "Hồ sơ của bạn đã được cập nhật thành công.");

        return "redirect:/my-account";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("repeatPassword") String repeatPassword,
                                 HttpServletRequest request, RedirectAttributes redirectAttributes,
                                 Model model) {
        User currentUser = (User) request.getSession().getAttribute("loggedInUser");
        populateModelWithUserDetails(currentUser, (User) model.getAttribute("user"), model);

        if (!currentPassword.equals(currentUser.getPassword())) {
            model.addAttribute("errorMessage", "Mật khẩu cũ không chính xác.");
            model.addAttribute("activeTab", "account-change-password");
            return "pages/my-account";
        }

        if (!newPassword.equals(repeatPassword)) {
            model.addAttribute("errorMessage", "Mật khẩu không trùng.");
            model.addAttribute("activeTab", "account-change-password");
            return "pages/my-account";
        }

        userService.changePassword(currentUser.getId(), newPassword);
        model.addAttribute("activeTab", "account-change-password");
        redirectAttributes.addFlashAttribute("successMessage", "Đổi mật khẩu thành công");
        return "redirect:/my-account";
    }

    @PostMapping("/set-default-address")
    public String setDefaultAddress(@RequestParam("addressId") Long addressId,
                                    RedirectAttributes redirectAttributes,
                                    Authentication authentication) {
        AuthUser currentUser = (AuthUser) authentication.getPrincipal();
        userAddressService.setDefaultAddress(addressId, currentUser.getId());
        redirectAttributes.addFlashAttribute("successMessage", "Đổi mặc định thành công");
        return "redirect:/my-account";
    }

//    @GetMapping("/user-address")
//    public String getUserAddress(Model model, HttpServletRequest request) {
//        User currentUser = (User) request.getSession().getAttribute("loggedInUser");
//        List<UserAddress> userAddresses = userAddressService.findUserAddressById(currentUser.getId());
//        userAddresses.forEach(System.out::println);
//        model.addAttribute("userAddresses", userAddresses);
//        model.addAttribute("activeTab", "account-info");
//        return "pages/my-account";
//    }

    private void populateModelWithUserDetails(User currentUser, User user, Model model) {
        Optional<UserDetail> userDetail = userDetailService.getUserDetailByUserId(currentUser.getId());
        if (userDetail.isPresent()) {
            UpdateUserDetailDTO update = UpdateUserDetailDTO.builder()
                    .mobile(userDetail.get().getMobile())
                    .gender(userDetail.get().getGender())
                    .email(userDetail.get().getEmail())
                    .password(user.getPassword())
                    .build();
            model.addAttribute("updateUser", update);
        }
    }

}
