package org.sep2.repository;

import org.sep2.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
    // 기업 코드를 기반으로 기업 정보를 조회하는 메서드
    Company findByCompanyCode(String companyCode);
}