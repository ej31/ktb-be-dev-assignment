package com.ktb.assignment.repository;

import com.ktb.assignment.domain.StocksHistory;
import com.ktb.assignment.domain.StocksHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StocksHistoryRepository extends JpaRepository<StocksHistory, StocksHistoryId> {
    List<StocksHistory> findByCompany_CompanyCodeAndTradeDateBetween(
            @Param("companyCode") String companyCode,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );
}
