package com.italy.agrifood.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "businesses")
public class Business {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(nullable = true)
    private String keyword;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true, length = 500)
    private String pageLink;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "business_customers",
        joinColumns = @JoinColumn(name = "business_id"),
        inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> customers;

}
