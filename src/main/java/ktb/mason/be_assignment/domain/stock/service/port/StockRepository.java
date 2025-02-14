package ktb.mason.be_assignment.domain.stock.service.port;

import ktb.mason.be_assignment.domain.stock.domain.StockInfo;

public interface StockRepository {
    StockInfo findAllByCompanyCode(String companyCode, String startDate, String endDate);
}
