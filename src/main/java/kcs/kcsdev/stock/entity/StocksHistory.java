package kcs.kcsdev.stock.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class StocksHistory {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long id;

      @Column(name = "trade_date", nullable = false)
      private LocalDate tradeDate;

      @Column(name = "open_price", nullable = false)
      private Long openPrice;

      @Column(name = "high_price", nullable = false)
      private Long highPrice;

      @Column(name = "low_price", nullable = false)
      private Long lowPrice;

      @Column(name = "close_price", nullable = false)
      private Long closePrice;

      @Column(name = "volume", nullable = false)
      private Long volume;

      @ManyToOne
      @JoinColumn(name = "company_code", referencedColumnName = "company_code")
      private Company company;
}
