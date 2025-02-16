package org.ktb.dev.assignment.repository;

import org.ktb.dev.assignment.domain.StocksHistory;
import org.ktb.dev.assignment.presentation.v1.dto.StockPriceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StocksHistoryRepository extends JpaRepository<StocksHistory, String> {

    @Query("SELECT new org.ktb.dev.assignment.presentation.v1.dto.StockPriceDto(sh.tradeDate, sh.closePrice) " +
            "FROM StocksHistory sh " +
            "WHERE sh.companyCode = :companyCode " +
            "AND sh.tradeDate BETWEEN :startDate AND :endDate " +
            "ORDER BY sh.tradeDate")
    List<StockPriceDto> findStockPricesByCompanyAndDateRange(
            @Param("companyCode") String companyCode,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
