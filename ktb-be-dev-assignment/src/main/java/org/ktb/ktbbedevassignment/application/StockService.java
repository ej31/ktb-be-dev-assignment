package org.ktb.ktbbedevassignment.application;

import java.util.List;
import org.ktb.ktbbedevassignment.exception.CompanyNotFoundException;
import org.ktb.ktbbedevassignment.infrastructure.CompanyRepository;
import org.ktb.ktbbedevassignment.dto.StockInfoDto;
import org.ktb.ktbbedevassignment.infrastructure.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final CompanyRepository companyRepository;

    public StockService(StockRepository stockRepository, CompanyRepository companyRepository) {
        this.stockRepository = stockRepository;
        this.companyRepository = companyRepository;
    }

    public List<StockInfoDto> getStockInfo(String companyCode, String startDate, String endDate) {
        if (!companyRepository.existsByCompanyCode(companyCode)) {
            throw new CompanyNotFoundException(companyCode);
        }

        return stockRepository.findStockInfoList(companyCode, startDate, endDate);
    }
}
