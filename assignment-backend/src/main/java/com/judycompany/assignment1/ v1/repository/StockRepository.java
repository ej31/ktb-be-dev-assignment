package com.judycompany.assignment1.v1.repository;

import com.judycompany.assignment1.v1.model.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<StockHistory, Long> {
    List<StockHistory> findByCompanyCodeAndTradeDateBetween(String companyCode, LocalDate startDate, LocalDate endDate);
}
