package com.italy.agrifood.controller;

import com.italy.agrifood.entity.Customer;
import com.italy.agrifood.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/add")
    public String showAddCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "addCustomer";
    }

    @PostMapping("/add")
    public String addCustomer(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        customerService.saveCustomer(customer);
        redirectAttributes.addFlashAttribute("successMessage", "Customer added successfully!");
        return "redirect:/customers";
    }

    @GetMapping
    public String showAllCustomers(Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerService.getAllCustomers(pageable);

        model.addAttribute("customers", customerPage.getContent());
        model.addAttribute("customerPage", customerPage);
        model.addAttribute("currentPage", customerPage.getNumber());
        model.addAttribute("totalPages", customerPage.getTotalPages());
        model.addAttribute("pageSize", customerPage.getSize());

        return "customers";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.findById(id);
        if (customer == null) {
            model.addAttribute("errorMessage", "Customer not found.");
            return "redirect:/customers";
        }
        model.addAttribute("customer", customer);
        return "updateCustomer";
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Customer name cannot be empty.");
            return "redirect:/customers/update/" + id;
        }

        try {
            customerService.updateCustomer(id, customer);
            redirectAttributes.addFlashAttribute("successMessage", "Customer updated successfully!");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Customer not found.");
        }
        return "redirect:/customers";
    }

    @PostMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            customerService.deleteCustomer(id);
            redirectAttributes.addFlashAttribute("successMessage", "Customer deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting customer.");
        }
        return "redirect:/customers";
    }

    @GetMapping("/search")
    public String searchCustomers(@RequestParam("query") String query, Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> searchResults = customerService.searchCustomersByName(query, pageable);

        model.addAttribute("customers", searchResults.getContent());
        model.addAttribute("customerPage", searchResults);
        model.addAttribute("query", query);
        model.addAttribute("currentPage", searchResults.getNumber());
        model.addAttribute("totalPages", searchResults.getTotalPages());
        model.addAttribute("pageSize", searchResults.getSize());

        return "customers";
    }
}
