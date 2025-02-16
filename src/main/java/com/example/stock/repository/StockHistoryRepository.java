package com.example.stock.repository;

import com.example.stock.entity.StockHistory;
import com.example.stock.entity.StockHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StockHistory, StockHistoryId> {
    List<StockHistory> findByCompanyCodeAndTradeDateBetween(String companyCode, LocalDate startDate, LocalDate endDate);
}
