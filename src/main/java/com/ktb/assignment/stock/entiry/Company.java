package com.ktb.assignment.stock.entiry;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "company_code")
 // NOTE : 기업 코드
    private String companyCode;

    @Column(nullable = false)
    // NOTE : 기업명
    private String companyName;
}