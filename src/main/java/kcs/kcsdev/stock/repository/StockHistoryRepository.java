package kcs.kcsdev.stock.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import kcs.kcsdev.stock.entity.StocksHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockHistoryRepository extends JpaRepository<StocksHistory, Long> {

      Optional<List<StocksHistory>> findByCompanyCompanyCodeAndTradeDateBetween(String companyCode,
              LocalDate startDate, LocalDate endDate);
}
