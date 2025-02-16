package org.sep2.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stocks_history")
@Getter
@Setter
@NoArgsConstructor
public class StockHistory {

    @EmbeddedId
    private StockHistoryId id;

    @ManyToOne
    @JoinColumn(name = "company_code", referencedColumnName = "company_code", insertable = false, updatable = false)
    private Company company;

    @Column(nullable = false)
    private float openPrice;

    @Column(nullable = false)
    private float highPrice;

    @Column(nullable = false)
    private float lowPrice;

    @Column(nullable = false)
    private float closePrice;

    @Column(nullable = false)
    private float volume;


}