package org.covy.ktbbedevassignment.repository;

import org.covy.ktbbedevassignment.domain.StockHistory;
import org.covy.ktbbedevassignment.domain.StockHistory.StockHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// DB 접근

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory, StockHistoryId> {

    List<StockHistory> findByCompanyCompanyCodeAndTradeDateBetween(String companyCode, LocalDate startDate, LocalDate endDate);

}

