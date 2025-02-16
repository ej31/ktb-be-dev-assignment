package com.example.stockApi.repository.stock;

import com.example.stockApi.dto.stock.StockHistoryResponse;
import com.example.stockApi.entity.StocksHistoryEntity;
import com.example.stockApi.entity.StocksHistoryEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StocksHistoryRepository extends JpaRepository<StocksHistoryEntity, StocksHistoryEntityPK> {
    @Query("""
        SELECT new com.example.stockApi.dto.stock.StockHistoryResponse(
            c.companyName, s.tradeDate, s.closePrice
        )
        FROM StocksHistoryEntity s
        JOIN CompanyEntity c ON s.companyCode = c.companyCode
        WHERE s.companyCode = :companyCode
        AND s.tradeDate BETWEEN :startDate AND :endDate
    """)
    List<StockHistoryResponse> findStockHistoryWithCompanyName(
            @Param("companyCode") String companyCode,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
