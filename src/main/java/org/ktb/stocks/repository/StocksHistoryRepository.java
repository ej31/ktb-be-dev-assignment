package org.ktb.stocks.repository;

import org.ktb.stocks.domain.StocksHistory;
import org.ktb.stocks.dto.ClosingPriceDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface StocksHistoryRepository extends R2dbcRepository<StocksHistory, Long> {
    @Query("SELECT * FROM stocks_history limit :limit")
    Flux<StocksHistory> findAll(int limit);

    @Query("""
            SELECT
                s.id, c.company_name, s.trade_date, s.close_price
            FROM stocks_history as s
            LEFT JOIN company as c
                ON s.company_code = c.company_code
            WHERE c.company_code LIKE :companyCode
                and s.trade_date BETWEEN :startDate AND :endDate
            ORDER BY
                s.trade_date,
                s.id;
            """)
    Flux<ClosingPriceDTO> closingPriceBetweenDates(String companyCode, LocalDate startDate, LocalDate endDate);
}
