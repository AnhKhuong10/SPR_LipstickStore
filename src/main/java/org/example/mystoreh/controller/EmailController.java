package org.example.mystoreh.controller;

import jakarta.mail.MessagingException;
import org.example.mystoreh.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("")
public class EmailController {

    private EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/sendMail")
    public String sendEmail(){
        try {
            emailService.sendMessage("MyStoreH",
                    "Đặt hàng thành công",
                    "Cảm ơn bạn đã đặt hàng",
                    "anhkhuong989@gmail.com"
            );
        } catch (MessagingException | UnsupportedEncodingException e) {
            System.out.println("not oke");
        }
        return "redirect:/main/pages/products";
    }
}
