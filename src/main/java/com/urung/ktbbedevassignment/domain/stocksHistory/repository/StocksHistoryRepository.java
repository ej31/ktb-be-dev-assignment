package com.urung.ktbbedevassignment.domain.stocksHistory.repository;

import com.urung.ktbbedevassignment.domain.stocksHistory.entity.StocksHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StocksHistoryRepository extends JpaRepository<StocksHistory, String> {
}
