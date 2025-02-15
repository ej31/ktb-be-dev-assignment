package com.example.stockapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_code", nullable = false, unique = true)
    private String companyCode;

    @Column(name = "company_name", nullable = false)
    private String companyName;
}

