package com.italy.agrifood.controller;

import com.italy.agrifood.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgotPassword";
    }

    @PostMapping("/forgot-password")
    public String handleForgotPassword(@RequestParam("email") String email) {
        // Handle password reset request logic here
        // For example, send an email with a password reset link to the user
        boolean emailSent = userService.sendPasswordResetEmail(email);

        if (emailSent) {
            return "redirect:/forgot-password?success"; // Or show a success message on the page
        } else {
            return "redirect:/forgot-password?error"; // Show an error message
        }
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage() {
        return "resetPassword";
    }
}
