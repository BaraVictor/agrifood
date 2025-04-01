package com.italy.agrifood.controller;

import com.italy.agrifood.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        boolean emailSent = userService.sendPasswordResetEmail(email);

        if (emailSent) {
            return "redirect:/forgot-password?success";
        } else {
            return "redirect:/forgot-password?error";
        }
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage() {
        return "resetPassword";
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // Shows the registration form
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           @RequestParam String role,
                           @RequestParam(required = false) String adminKey,
                           Model model) {

        if (!role.equalsIgnoreCase("VIEWER")) {
            boolean isValidKey = userService.isValidTemporaryKey(adminKey, role);
            if (!isValidKey) {
                model.addAttribute("errorMessage", "Invalid or expired key for selected role.");
                return "register";
            }
        }

        try {
            userService.registerNewUser(username, email, password, role);
            return "redirect:/login?registered";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }
}
