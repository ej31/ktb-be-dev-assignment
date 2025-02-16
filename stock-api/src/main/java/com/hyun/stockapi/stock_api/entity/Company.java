package com.hyun.stockapi.stock_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "company")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company {

    @Id
    @Column(name = "company_code", length = 10, nullable = false)
    private String companyCode;

    @Column(name = "company_name", length = 100, nullable = false)
    private String companyName;

}
