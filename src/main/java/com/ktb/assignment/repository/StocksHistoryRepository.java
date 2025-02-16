package com.ktb.assignment.repository;

import com.ktb.assignment.domain.StocksHistory;
import com.ktb.assignment.domain.StocksHistoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StocksHistoryRepository extends JpaRepository<StocksHistory, StocksHistoryId> {

}
