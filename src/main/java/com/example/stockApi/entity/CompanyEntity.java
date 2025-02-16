package com.example.stockApi.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "company", schema = "assignment")
public class CompanyEntity {
    @Id
    @Column(name = "company_code", nullable = false, length = 10)
    private String companyCode;

    @Column(name = "company_name", nullable = false)
    private String companyName;
}
