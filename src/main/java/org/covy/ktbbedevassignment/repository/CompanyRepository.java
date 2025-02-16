package org.covy.ktbbedevassignment.repository;

import org.covy.ktbbedevassignment.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
}

