package com.italy.agrifood.repo;

import com.italy.agrifood.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Long> {

    List<Customer> findByNameContainingIgnoreCase(String name);

    Page<Customer> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email, Pageable pageable);

    List<Customer> findByPhoneContaining(String phone);

    Page<Customer> findAll(Pageable pageable);

    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Customer> findByEmailContainingIgnoreCase(String email, Pageable pageable);
}
