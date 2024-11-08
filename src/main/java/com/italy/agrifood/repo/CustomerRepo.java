package com.italy.agrifood.repo;

import com.italy.agrifood.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    // Method to search customers by name (case insensitive)
    List<Customer> findByNameContainingIgnoreCase(String name);

    // Pagination method for searching by name or email (case insensitive)
    Page<Customer> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Pageable pageable);

    // Method for finding customers by phone number
    List<Customer> findByPhoneContaining(String phone);

    // Pagination method for retrieving all customers
    Page<Customer> findAll(Pageable pageable);

    // Pagination method for searching by name (case insensitive)
    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Pagination method for searching by email (case insensitive)
    Page<Customer> findByEmailContainingIgnoreCase(String email, Pageable pageable);
}
