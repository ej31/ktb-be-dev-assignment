package com.example.stockApi.repository.stock;

import com.example.stockApi.entity.StocksHistoryEntity;
import com.example.stockApi.entity.StocksHistoryEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StocksHistoryRepository extends JpaRepository<StocksHistoryEntity, StocksHistoryEntityPK> {
    List<StocksHistoryEntity> findByCompanyCodeAndTradeDateBetween(String companyCode, LocalDate startDate, LocalDate endDate);
}
