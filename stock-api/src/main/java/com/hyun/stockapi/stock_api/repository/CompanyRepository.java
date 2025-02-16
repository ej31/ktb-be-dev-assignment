package com.hyun.stockapi.stock_api.repository;

import com.hyun.stockapi.stock_api.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

}
