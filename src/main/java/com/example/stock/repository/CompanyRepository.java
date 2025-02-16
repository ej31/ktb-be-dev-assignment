package com.example.stock.repository;

import com.example.stock.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long>{
    Optional<Company> findByCompanyCode(String companyCode);
}