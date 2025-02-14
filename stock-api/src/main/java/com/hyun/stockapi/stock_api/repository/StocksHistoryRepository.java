package com.hyun.stockapi.stock_api.repository;

import com.hyun.stockapi.stock_api.entity.StocksHistory;
import com.hyun.stockapi.stock_api.entity.StocksHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Repository
public interface StocksHistoryRepository extends JpaRepository<StocksHistory, StocksHistoryId> {

    List<StocksHistory> findByCompanyCodeAndTradeDateBetween(
            String companyCode,
            LocalDate tradeDate
    );
}
