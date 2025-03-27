package com.italy.agrifood.controller;

import com.italy.agrifood.entity.User;
import com.italy.agrifood.service.RoleService;
import com.italy.agrifood.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('VIEWER', 'WORKER', 'MANAGER')")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAnyRole('WORKER', 'MANAGER')")
    public String showAddUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "addUser";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('WORKER', 'MANAGER')")
    public String addUser(@ModelAttribute User user, @RequestParam("role") String roleName, RedirectAttributes redirectAttributes) {
        try {
            userService.createUser(user.getUsername(), user.getPassword(), user.getEmail(), roleName);
            redirectAttributes.addFlashAttribute("successMessage", "User added successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding user: " + e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('WORKER', 'MANAGER')")
    public String showUpdateForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
            model.addAttribute("roles", roleService.getAllRoles());
            return "updateUser"; // Return Thymeleaf view for updating a user
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "User not found.");
            return "redirect:/users";
        }
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('WORKER', 'MANAGER')")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user, @RequestParam("role") String roleName, RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(id, user, roleName);
            redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating user: User not found.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating user: " + e.getMessage());
        }
        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('MANAGER')")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting user: User not found.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting user: " + e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }
}
