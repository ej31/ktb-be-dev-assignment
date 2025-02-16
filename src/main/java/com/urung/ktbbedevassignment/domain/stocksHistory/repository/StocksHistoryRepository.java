package com.urung.ktbbedevassignment.domain.stocksHistory.repository;

import com.urung.ktbbedevassignment.api.v1.domain.dto.StockPriceDto;
import com.urung.ktbbedevassignment.domain.stocksHistory.entity.StocksHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface StocksHistoryRepository extends JpaRepository<StocksHistory, String> {
    List<StocksHistory> findByCompanyCodeAndTradeDateBetween(
            String companyCode,
            LocalDate startDate,
            LocalDate endDate
    );
    @Query("SELECT sh FROM StocksHistory sh " +
            "WHERE sh.companyCode = :companyCode " +
            "AND sh.tradeDate BETWEEN :startDate AND :endDate " +
            "ORDER BY sh.tradeDate")
    List<StocksHistory> findStockHistories(
            @Param("companyCode") String companyCode,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
