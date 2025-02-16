package com.urung.ktbbedevassignment.domain.stocksHistory.repository;

import com.urung.ktbbedevassignment.domain.stocksHistory.entity.StocksHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface StocksHistoryRepository extends JpaRepository<StocksHistory, String> {
    List<StocksHistory> findByCompanyCodeAndTradeDateBetween(
            String companyCode,
            LocalDate startDate,
            LocalDate endDate
    );
}
