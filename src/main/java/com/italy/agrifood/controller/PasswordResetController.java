package com.italy.agrifood.controller;

import com.italy.agrifood.entity.User;
import com.italy.agrifood.repo.UserRepo;
import com.italy.agrifood.service.SecurityCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class PasswordResetController {

  @Autowired
  private UserRepo userRepository;

  @Autowired
  private SecurityCodeService securityCodeService;

  @GetMapping("/forgot-password")
  public String showForgotPasswordPage() {
    return "forgotPassword"; // Already implemented
  }

  @PostMapping("/forgot-password")
  public String forgotPassword(@RequestParam String email, Model model) {
    try {
      User user = userRepository.findByEmail(email)
              .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

      String code = securityCodeService.generateCode(email);
      System.out.println("DEBUG: Security code for " + email + " is: " + code); // Useful during testing

      // Redirect to code verification page
      return "redirect:/auth/verify-code?email=" + email;
    } catch (RuntimeException e) {
      model.addAttribute("error", e.getMessage());
      return "forgotPassword";
    }
  }

  @GetMapping("/verify-code")
  public String showCodeVerificationPage(@RequestParam String email, Model model) {
    model.addAttribute("email", email);
    return "verifyCode"; // You’ll add this HTML
  }

  @PostMapping("/verify-code")
  public String verifyCode(@RequestParam String email, @RequestParam String code, Model model) {
    if (securityCodeService.validateCode(email, code)) {
      model.addAttribute("token", email); // use email as pseudo-token
      securityCodeService.clearCode(email); // optional: clear after success
      return "resetPassword"; // resetPassword.html already exists
    } else {
      model.addAttribute("email", email);
      model.addAttribute("error", "Invalid verification code");
      return "verifyCode";
    }
  }

  @GetMapping("/reset-password")
  public String showResetPasswordPage() {
    return "resetPassword";
  }

  @PostMapping("/reset-password")
  public String resetPassword(@RequestParam String token,
                              @RequestParam String newPassword,
                              @RequestParam String confirmPassword,
                              Model model) {
    try {
      if (!newPassword.equals(confirmPassword)) {
        model.addAttribute("token", token);
        model.addAttribute("error", "Passwords do not match");
        return "resetPassword";
      }

      User user = userRepository.findByEmail(token)
              .orElseThrow(() -> new RuntimeException("Invalid token: User not found"));

      user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
      userRepository.save(user);

      return "redirect:/login?resetSuccess"; // or show a success page
    } catch (RuntimeException e) {
      model.addAttribute("error", e.getMessage());
      return "resetPassword";
    }
  }
}
