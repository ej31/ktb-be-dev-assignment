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
    @Column(name = "company_code", length = 10, nullable = false)
    private String companyCode;

    @Column(name = "company_name", length = 100, nullable = false)
    private String companyName;
}

