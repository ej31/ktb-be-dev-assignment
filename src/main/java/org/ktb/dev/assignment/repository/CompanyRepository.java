package org.ktb.dev.assignment.repository;

import org.ktb.dev.assignment.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
    @Query("SELECT c.companyName FROM Company c WHERE c.companyCode = :companyCode")
    Optional<String> findCompanyNameByCode(@Param("companyCode") String companyCode);
}
