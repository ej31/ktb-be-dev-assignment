package com.example.stock.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "company_code", nullable = false, unique = true)
    private String companyCode;

    @Column(name = "company_name", nullable = false)
    private String companyName;
}

