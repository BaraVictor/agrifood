package com.italy.agrifood.service;

import com.italy.agrifood.entity.Business;
import com.italy.agrifood.repo.BusinessRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BusinessService {

    private final BusinessRepo businessRepository;

    private static final String[] KEYWORDS = {"olio", "formaggio", "vino", "prodotti artigianali", "frutta", "conserve", "legumi", "spezie"};

    @Autowired
    public BusinessService(BusinessRepo businessRepository) {
        this.businessRepository = businessRepository;
    }

    public void saveBusiness(Business business) {
        businessRepository.save(business);
    }

    private String generateBusinessId() {
        return "BUS" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void updateBusiness(Long id, Business business) {
        Optional<Business> existingBusinessOptional = businessRepository.findById(id);
        if (existingBusinessOptional.isPresent()) {
            Business existingBusiness = existingBusinessOptional.get();
            existingBusiness.setName(business.getName());
            existingBusiness.setDescription(business.getDescription());
            existingBusiness.setKeyword(business.getKeyword());
            existingBusiness.setPageLink(business.getPageLink());
            existingBusiness.setId(business.getId());
            businessRepository.save(existingBusiness);
        } else {
            throw new EntityNotFoundException("Business with ID " + id + " not found");
        }
    }

    public Business findById(Long id) {
        return businessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Business not found with id " + id));
    }

    public void deleteBusiness(Long id) {
        businessRepository.deleteById(id);
    }

    public List<String> getAllKeywords() {
        return Arrays.asList(KEYWORDS);
    }

    public Map<String, List<Business>> getBusinessesGroupedByKeyword() {
        List<Business> businesses = businessRepository.findAll();
        return businesses.stream().collect(Collectors.groupingBy(Business::getKeyword));
    }

    public Page<Business> getAllBusinesses(Pageable pageable) {
        return businessRepository.findAll(pageable);
    }

    public Page<Business> findBusinessesByKeyword(String keyword, Pageable pageable) {
        if (keyword == null || keyword.isEmpty() || keyword.equalsIgnoreCase("Tutti")) {
            return businessRepository.findAll(pageable);
        } else {
            return businessRepository.findByKeyword(keyword, pageable);
        }
    }

    public Page<Business> searchBusinessesByName(String name, Pageable pageable) {
        return businessRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Page<Business> searchBusinessesByNameOrDescription(String name, String description, Pageable pageable) {
        return businessRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(name, description, pageable);
    }
}
