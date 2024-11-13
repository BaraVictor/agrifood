package com.italy.agrifood.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @GetMapping("/add")
    public String showAddBusinessPage() {
        return "addBusiness";
    }
    @GetMapping("/welcome")
    public String welcome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        model.addAttribute("role", role);
        return "welcome";
    }
}
