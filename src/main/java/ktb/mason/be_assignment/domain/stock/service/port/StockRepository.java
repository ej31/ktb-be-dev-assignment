package ktb.mason.be_assignment.domain.stock.service.port;

import ktb.mason.be_assignment.domain.stock.domain.StockInfo;

import java.util.Optional;

public interface StockRepository {
    Optional<StockInfo> findStockInfoByCompanyAndDateRange(String companyCode, String startDate, String endDate);
}
