package com.jenny.ktb_be_dev_assignment.repository;

import com.jenny.ktb_be_dev_assignment.entity.StockHistory;
import com.jenny.ktb_be_dev_assignment.entity.StockHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory, StockHistoryId> {

    List<StockHistory> findByCompanyCodeAndTradeDateBetweenOrderByTradeDate(
            String companyCode, LocalDate startDate, LocalDate endDate
    );
}
