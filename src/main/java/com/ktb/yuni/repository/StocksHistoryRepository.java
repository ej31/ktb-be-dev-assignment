package com.ktb.yuni.repository;

import com.ktb.yuni.domain.StocksHistory;
import com.ktb.yuni.domain.StocksHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StocksHistoryRepository extends JpaRepository<StocksHistory, StocksHistoryId> {
    List<StocksHistory> findByCompanyCodeAndTradeDateBetween(
            String companyCode,
            LocalDate startDate,
            LocalDate endDate
    );
}