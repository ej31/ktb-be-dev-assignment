package com.example.stockapi.repository;

import com.example.stockapi.entity.StockHistory;
import com.example.stockapi.entity.StockHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, StockHistoryId> {
    List<StockHistory> findByCompanyCodeAndTradeDate(String companyCode, LocalDate startDate, LocalDate endDate);
}

