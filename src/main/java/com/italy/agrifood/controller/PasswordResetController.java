package com.italy.agrifood.controller;

import com.italy.agrifood.entity.User;
import com.italy.agrifood.repo.UserRepo;
import com.italy.agrifood.service.EmailService;
import com.italy.agrifood.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class PasswordResetController {

  @Autowired
  private UserRepo userRepository;

  @Autowired
  private EmailService emailService;

  @Autowired
  private TokenService tokenService;

  @GetMapping("/forgot-password")
  public String showForgotPasswordPage() {
    return "forgotPassword";  // Ensure you have the correct template name
  }

  @GetMapping("/reset-password")
  public String showResetPasswordPage() {
    return "resetPassword";  // This should match your template name
  }

  // Endpoint to request a password reset
  @PostMapping("/forgot-password")
  public ResponseEntity<String> forgotPassword(@RequestParam String email) {
    try {
      User user = userRepository.findByEmail(email)
          .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

      // Generate reset token and send recovery email
      String token = tokenService.generateToken(user);
      String resetLink = "http://your-domain.com/reset-password?token=" + token;
      emailService.sendPasswordResetEmail(user.getEmail(), resetLink); // Implement this method in EmailService

      return ResponseEntity.ok("Password recovery email sent");
    } catch (RuntimeException e) {
      return ResponseEntity.status(400).body(e.getMessage());
    }
  }

  // Endpoint to reset password using the token
  @PostMapping("/reset-password")
  public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
    try {
      String email = tokenService.extractEmailFromToken(token);

      User user = userRepository.findByEmail(email)
          .orElseThrow(() -> new RuntimeException("Invalid token: User not found"));

      user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
      userRepository.save(user);

      return ResponseEntity.ok("Password has been reset");
    } catch (RuntimeException e) {
      return ResponseEntity.status(400).body(e.getMessage());
    }
  }
}
