package com.italy.agrifood.repo;

import com.italy.agrifood.entity.Business;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusinessRepo extends JpaRepository<Business, Long> {
    // Interogare pentru a obține o listă distinctă de keyword-uri
    @Query("SELECT DISTINCT b.keyword FROM Business b WHERE b.keyword IS NOT NULL")
    List<String> findDistinctKeywords();

    // Metodă pentru a căuta afaceri care conțin un anumit text în nume
    List<Business> findByNameContainingIgnoreCase(String name);

    // Metodă pentru a obține toate afacerile ordonate după keyword
    List<Business> findAllByOrderByKeywordAsc();

    Page<Business> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description, Pageable pageable);
    Page<Business> findByKeywordOrderByKeywordAsc(String keyword, Pageable pageable);

    List<Business> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);

    List<Business> findByKeywordOrderByKeywordAsc(String keyword);

    Page<Business> findAll(Pageable pageable);

    Page<Business> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Business> findByKeyword(String keyword, Pageable pageable);
}