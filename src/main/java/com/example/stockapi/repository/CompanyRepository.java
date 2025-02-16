package com.example.stockapi.repository;

import com.example.stockapi.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
    Company findByCompanyCode(String companyCode);
}


