//package org.example.mystoreh.controller.user_login_logout_registration;
//
//import jakarta.servlet.http.HttpSession;
//import org.example.mystoreh.entity.User;
//import org.example.mystoreh.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@SessionAttributes("user")
//public class LoginController {
//    private UserService userService;
//
//    @Autowired
//    public LoginController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @ModelAttribute("user")
//    public User user() {
//        return new User();
//    }
//
//    @GetMapping("/login")
//    public String showLoginForm() {
//        return "pages/login";
//    }
//
//    @PostMapping("/checkLogin")
//    public String login(@ModelAttribute("user") User user, @RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session) {
//        if (userService.checkUserByUsername(username) == false) {
//            model.addAttribute("ERROR", "Username or password not exist");
//            model.addAttribute("username", username);
//            return "pages/login";
//        }
//        user = userService.checkPasswordUser(username, password).orElse(null);
//        if (user != null) {
//            session.setAttribute("loggedInUser", user);
//            return "redirect:/main/products";
//        }
//        model.addAttribute("ERROR", "Username or password not exist");
//        model.addAttribute("username", username);
//        model.addAttribute("password", password);
//        return "pages/login";
//    }
//
//    @GetMapping("/logout")
//    public String logout(@ModelAttribute("user") User user, HttpSession session) {
//        session.removeAttribute("loggedInUser");
//        return "redirect:/login";
//    }
//}
