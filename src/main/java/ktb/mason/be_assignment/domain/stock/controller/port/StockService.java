package ktb.mason.be_assignment.domain.stock.controller.port;

import ktb.mason.be_assignment.domain.stock.controller.response.StockInfoResponse;

public interface StockService {
    StockInfoResponse getStockInfoByCompanyCode(String companyCode, String startDate, String endDate);
}
