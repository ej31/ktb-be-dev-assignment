package org.sep2.repository;

import org.sep2.domain.StockHistory;
import org.sep2.domain.StockHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory, StockHistoryId> {

    // 특정 기업의 특정 기간 동안 주식 데이터를 조회하는 메서드
    List<StockHistory> findByIdCompanyCodeAndIdTradeDateBetween(
            String companyCode,
            LocalDate startDate,
            LocalDate endDate
    );
}