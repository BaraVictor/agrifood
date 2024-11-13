package com.italy.agrifood.controller;

import com.italy.agrifood.entity.Business;
import com.italy.agrifood.service.BusinessService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/businesses")
public class BusinessController {

    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @GetMapping("/add")
    public String showAddBusinessForm(Model model) {
        model.addAttribute("business", new Business());
        return "addBusiness";
    }

    @PostMapping("/add")
    public String addBusiness(@ModelAttribute Business business, RedirectAttributes redirectAttributes) {
        businessService.saveBusiness(business);
        redirectAttributes.addFlashAttribute("successMessage", "Business added successfully!");
        return "redirect:/businesses";
    }

    @GetMapping
    public String showAllBusinesses(Model model,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Business> businessPage = businessService.getAllBusinesses(pageable);

        model.addAttribute("businesses", businessPage.getContent());
        model.addAttribute("businessPage", businessPage);
        model.addAttribute("currentPage", businessPage.getNumber());
        model.addAttribute("totalPages", businessPage.getTotalPages());
        model.addAttribute("pageSize", businessPage.getSize());
        model.addAttribute("keywords", businessService.getAllKeywords());

        return "businesses";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Business business = businessService.findById(id);
        if (business == null) {
            model.addAttribute("errorMessage", "Business not found.");
            return "redirect:/businesses";
        }
        model.addAttribute("business", business);

        List<String> keywords = Arrays.asList("Olio", "Formaggio", "Vino", "Prodotti Artigianali", "Frutta", "Conserve", "Legumi", "Spezie");
        model.addAttribute("keywords", keywords);

        return "updateBusiness";
    }

    @PostMapping("/update/{id}")
    public String updateBusiness(@PathVariable Long id, @ModelAttribute Business business, RedirectAttributes redirectAttributes) {
        if (business.getName() == null || business.getName().trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Business name cannot be empty.");
            return "redirect:/businesses/update/" + id;
        }

        try {
            businessService.updateBusiness(id, business);
            redirectAttributes.addFlashAttribute("successMessage", "Business updated successfully!");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Business not found.");
        }
        return "redirect:/businesses";
    }

    @PostMapping("/delete/{id}")
    public String deleteBusiness(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            businessService.deleteBusiness(id);
            redirectAttributes.addFlashAttribute("successMessage", "Business deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting business.");
        }
        return "redirect:/businesses";
    }

    @GetMapping("/search")
    public String searchBusinesses(@RequestParam("query") String query, Model model,
                                   @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Business> searchResults = businessService.searchBusinessesByName(query, pageable);

        model.addAttribute("businesses", searchResults.getContent());
        model.addAttribute("businessPage", searchResults);
        model.addAttribute("query", query);
        model.addAttribute("currentPage", searchResults.getNumber());
        model.addAttribute("totalPages", searchResults.getTotalPages());
        model.addAttribute("pageSize", searchResults.getSize());
        model.addAttribute("keywords", businessService.getAllKeywords());

        return "businesses";
    }

    @GetMapping("/sort")
    public String sortBusinessesByKeyword(@RequestParam(value = "keyword", required = false) String keyword,
                                          Model model,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Business> businessPage;

        if (keyword == null || keyword.isEmpty() || keyword.equals("Tutti")) {
            businessPage = businessService.getAllBusinesses(pageable);
        } else {
            businessPage = businessService.findBusinessesByKeyword(keyword, pageable);
        }

        model.addAttribute("businesses", businessPage.getContent());
        model.addAttribute("businessPage", businessPage);
        model.addAttribute("keywords", businessService.getAllKeywords());
        model.addAttribute("selectedKeyword", keyword);
        model.addAttribute("currentPage", businessPage.getNumber());
        model.addAttribute("totalPages", businessPage.getTotalPages());
        model.addAttribute("pageSize", businessPage.getSize());

        return "businesses";
    }
}
