package com.italy.agrifood.service;

import com.italy.agrifood.entity.Customer;
import com.italy.agrifood.repo.CustomerRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepo customerRepository;

    @Autowired
    public CustomerService(CustomerRepo customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void saveCustomer(Customer customer) {
        if (customer.getCustomerId() == null || customer.getCustomerId().isEmpty()) {
            customer.setCustomerId(generateCustomerId());
        }
        customerRepository.save(customer);
    }

    private String generateCustomerId() {
        return "CUS" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public void updateCustomer(Long id, Customer customer) {
        Optional<Customer> existingCustomerOptional = customerRepository.findById(id);
        if (existingCustomerOptional.isPresent()) {
            Customer existingCustomer = existingCustomerOptional.get();
            existingCustomer.setName(customer.getName());
            existingCustomer.setEmail(customer.getEmail());
            existingCustomer.setPhoneNumber(customer.getPhoneNumber());

            if (customer.getCustomerId() == null || customer.getCustomerId().isEmpty()) {
                existingCustomer.setCustomerId(generateCustomerId());
            } else {
                existingCustomer.setCustomerId(customer.getCustomerId());
            }

            customerRepository.save(existingCustomer);
        } else {
            throw new EntityNotFoundException("Customer with ID " + id + " not found");
        }
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + id));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    // Methods with pagination
    public Page<Customer> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Page<Customer> searchCustomersByName(String name, Pageable pageable) {
        return customerRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Page<Customer> searchCustomersByNameOrEmail(String name, String email, Pageable pageable) {
        return customerRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(name, email, pageable);
    }
}
