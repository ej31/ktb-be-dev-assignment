package com.jenny.ktb_be_dev_assignment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company {
    @Id
    @Column(name = "company_code", length = 10)
    private String companyCode;

    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @OneToMany(mappedBy = "company")
    private List<StockHistory> stockHistories;
}
