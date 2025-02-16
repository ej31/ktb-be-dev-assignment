package org.sep2.repository;

import org.sep2.domain.StockHistory;
import org.sep2.domain.StockHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory, StockHistoryId> {
    // 특정 회사의 주식 히스토리 조회
    List<StockHistory> findCompanyStockHistory(String companyCode);
}
