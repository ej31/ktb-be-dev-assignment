package com.ktb.assignment.stock.entiry;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "stocks_history")
public class StocksHistory {

    @EmbeddedId
    private StocksHistoryId id;

    @ManyToOne
    @MapsId("companyCode")
    @JoinColumn(name = "company_code", referencedColumnName = "company_code")
    // NOTE : 복합키의 companyCode를 Company 엔티티와 매핑
    private Company company;

    @Column(name = "open_price", nullable = false)
    private float openPrice;

    @Column(name = "high_price", nullable = false)
    private float highPrice;

    @Column(name = "low_price", nullable = false)
    private float lowPrice;

    @Column(name = "close_price", nullable = false)
    private float closePrice;

    @Column(name = "volume", nullable = false)
    private float volume;
}