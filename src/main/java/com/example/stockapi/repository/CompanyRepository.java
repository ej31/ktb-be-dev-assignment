package com.example.stockapi.repository;

import com.example.stockapi.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByCompanyCode(String companyCode);
}

