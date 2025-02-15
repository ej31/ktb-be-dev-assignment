package com.hyun.stockapi.stock_api.repository;

import com.hyun.stockapi.stock_api.dto.StockResponseDto;
import com.hyun.stockapi.stock_api.entity.StocksHistory;
import com.hyun.stockapi.stock_api.entity.StocksHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CustomStocksHistoryRepository extends JpaRepository<StocksHistory, StocksHistoryId> {
    @Query("SELECT new com.hyun.stockapi.stock_api.dto.StockResponseDto(c.companyName, s.tradeDate, s.closePrice) " +
            "FROM StocksHistory s " +
            "JOIN s.company c " +
            "WHERE s.companyCode = :companyCode " +
            "AND s.tradeDate = :tradeDate")
    List<StockResponseDto> findStockPrices(@Param("companyCode") String companyCode,
                                           @Param("tradeDate") LocalDate tradeDate);
}
