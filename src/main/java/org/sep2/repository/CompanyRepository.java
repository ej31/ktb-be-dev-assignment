package org.sep2.repository;

import org.sep2.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
    // company_code로 회사 찾기
    Company findCompanyCode(String companyCode);
}