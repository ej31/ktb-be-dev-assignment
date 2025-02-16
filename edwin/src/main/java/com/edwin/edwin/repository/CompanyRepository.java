package com.edwin.edwin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.edwin.edwin.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
    Optional<String> findByCode(@Param("Company_code") String code);
}
