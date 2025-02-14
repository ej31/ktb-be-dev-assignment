package com.ktb.assignment.stock.repository;

import com.ktb.assignment.stock.entiry.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findByCompanyCode(String companyCode);
}