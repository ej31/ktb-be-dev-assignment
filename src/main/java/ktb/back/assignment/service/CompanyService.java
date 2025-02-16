package ktb.back.assignment.service;

import ktb.back.assignment.dto.ResponseCompanyStockHistory;
import ktb.back.assignment.repository.StockHistoryRepositoryImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CompanyService {
    private final StockHistoryRepositoryImpl stockHistoryRepository;

    public CompanyService(StockHistoryRepositoryImpl stockHistoryRepository){
        this.stockHistoryRepository = stockHistoryRepository;
    }

    public List<ResponseCompanyStockHistory> getCompanyStockHistory(String companyCode, String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return stockHistoryRepository.findCompanyStockHistory(companyCode,start,end);
    }
}
