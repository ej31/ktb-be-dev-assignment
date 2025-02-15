package com.ktb.yuni.repository;

import com.ktb.yuni.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company,String> {
    Optional<Company> findByCompanyCode(String companyCode);
}
