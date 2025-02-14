package com.ktb.assignment.stock.repository;

import com.ktb.assignment.stock.entiry.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StocksHistoryRepository extends JpaRepository<StocksHistory, StocksHistoryId> {
    List<StocksHistory> findById_CompanyCodeAndId_TradeDateBetween(
        String companyCode, LocalDate startDate, LocalDate endDate);
}