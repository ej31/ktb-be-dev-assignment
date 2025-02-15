package ktb.mason.be_assignment.domain.stock.service.port;

import ktb.mason.be_assignment.domain.stock.domain.StockHistory;

import java.util.List;

public interface StockHistoryRepository {
    List<StockHistory> findAllByCompanyAndDateRange(String companyCode, String startDate, String endDate);
}
