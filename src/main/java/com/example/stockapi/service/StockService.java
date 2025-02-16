package com.example.stockapi.service;

import com.example.stockapi.dto.StockResponseDTO;
import com.example.stockapi.entity.Company;
import com.example.stockapi.entity.StockHistory;
import com.example.stockapi.repository.CompanyRepository;
import com.example.stockapi.repository.StockHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {
    private final StockHistoryRepository stockHistoryRepository;
    private final CompanyRepository companyRepository;

    public StockService(StockHistoryRepository stockHistoryRepository, CompanyRepository companyRepository) {
        this.stockHistoryRepository = stockHistoryRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional(readOnly = true)
    public List<StockResponseDTO> getStockData(String companyCode, LocalDate startDate, LocalDate endDate) {
        Company company = companyRepository.findByCompanyCode(companyCode);
        if (company == null) {
            throw new IllegalArgumentException("Invalid company code");
        }

        return stockHistoryRepository.findByCompanyCodeAndTradeDateBetween(companyCode, startDate, endDate)
                .stream()
                .map(stock -> new StockResponseDTO(
                        company.getCompanyName(),
                        stock.getTradeDate(),
                        stock.getOpenPrice(),
                        stock.getHighPrice(),
                        stock.getLowPrice(),
                        stock.getClosePrice(),
                        stock.getVolume()
                ))
                .collect(Collectors.toList());
    }
}
