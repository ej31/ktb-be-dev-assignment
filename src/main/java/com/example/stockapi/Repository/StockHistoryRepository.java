package com.example.stockapi.Repository;

import com.example.stockapi.entity.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
    List<StockHistory> findByCompanyCodeAndTradeDate(String companyCode, LocalDate startDate, LocalDate endDate);
}
