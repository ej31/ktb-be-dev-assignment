package com.urung.ktbbedevassignment.domain.company.entity;

import com.urung.ktbbedevassignment.global.config.BaseTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "company")
@NoArgsConstructor
@Entity
public class Company extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_code")
    private String companyCode;

    @Column(name = "company_name")
    private String companyName;
}
