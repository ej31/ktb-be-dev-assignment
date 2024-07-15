package org.ktb.stocks.repository;

import org.ktb.stocks.domain.StocksHistory;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface StocksHistoryRepository extends R2dbcRepository<StocksHistory, Long> {
    @Query("SELECT * FROM stocks_history limit :limit")
    public Flux<StocksHistory> findAll(int limit);
}
