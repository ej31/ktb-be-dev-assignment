package kcs.kcsdev.stock.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
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
public class Company {

      @Id
      @Column(name = "company_code", nullable = false, unique = true)
      private String companyCode;

      @Column(nullable = false)
      private String companyName;

      @Builder.Default
      @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
      private List<StocksHistory> stocksHistories = new ArrayList<>();

}
