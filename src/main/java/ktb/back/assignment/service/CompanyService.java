package ktb.back.assignment.service;

import ktb.back.assignment.dao.StockHistoryRepository;
import ktb.back.assignment.model.ResponseCompanyDto;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CompanyService {
    private final StockHistoryRepository stockHistoryRepository;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public CompanyService(StockHistoryRepository stockHistoryRepository){
        this.stockHistoryRepository = stockHistoryRepository;
    }

    public List<ResponseCompanyDto> getCompanyStockHistory(String companyCode, String startDate, String endDate) throws ParseException {
        Date start = dateFormat.parse(startDate);
        Date end = dateFormat.parse(endDate);
        return stockHistoryRepository.findCompanyStockHistory(companyCode,start,end);
    }
}
