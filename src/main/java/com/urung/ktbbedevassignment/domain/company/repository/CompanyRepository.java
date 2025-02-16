package com.urung.ktbbedevassignment.domain.company.repository;

import com.urung.ktbbedevassignment.domain.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, String>{
    @Query("SELECT c.companyName FROM Company c WHERE c.companyCode = :companyCode")
    Optional<String> findCompanyNameByCode(@Param("companyCode") String companyCode);
}
