package com.italy.agrifood.controller;

import com.italy.agrifood.service.TemporaryKeyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

  private final TemporaryKeyService temporaryKeyService;

  public AdminController(TemporaryKeyService temporaryKeyService) {
    this.temporaryKeyService = temporaryKeyService;
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @GetMapping("/generate-key")
  public String showGenerateKeyPage() {
    return "adminRoleKey"; // Show the form
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PostMapping("/generate-key")
  public String generateKey(@RequestParam(defaultValue = "ADMIN") String role, Model model) {
    try {
      String key = temporaryKeyService.generateKey(role);
      model.addAttribute("generatedKey", key);
    } catch (Exception e) {
      model.addAttribute("errorMessage", e.getMessage());
    }
    return "adminRoleKey";
  }
}
