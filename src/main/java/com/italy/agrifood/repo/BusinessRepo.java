package com.italy.agrifood.repo;

import com.italy.agrifood.entity.Business;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusinessRepo extends JpaRepository<Business, Long> {

    Page<Business> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description, Pageable pageable);

    Page<Business> findAll(Pageable pageable);

    Page<Business> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Business> findByKeyword(String keyword, Pageable pageable);
}