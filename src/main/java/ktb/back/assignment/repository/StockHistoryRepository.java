package ktb.back.assignment.repository;

import ktb.back.assignment.dto.ResponseCompanyStockHistory;

import java.time.LocalDate;
import java.util.List;

public interface StockHistoryRepository {

    public List<ResponseCompanyStockHistory> findCompanyStockHistory(String companyCode, LocalDate startDate, LocalDate endDate);
}
