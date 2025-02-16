package com.example.backend.repository;

import com.example.backend.entity.StocksHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StockHistoryRepository extends JpaRepository<StocksHistory, StocksHistory.StockHistoryId> {
    List<StocksHistory> findByCompanyCodeAndTradeDateBetween(String companyCode, LocalDate startDate, LocalDate endDate);
}
