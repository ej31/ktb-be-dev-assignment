package org.ktb.stocks.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class StocksHistory {
    @Id
    private final Long id;
    @Column("trade_date")
    private final LocalDate tradeDate;
    @Column("open_price")
    private final Long openPrice;
    @Column("high_price")
    private final Long highPrice;
    @Column("low_price")
    private final Long lowPrice;
    @Column("close_price")
    private final Long closePrice;
    @Column("volume")
    private final Long volume;
}
