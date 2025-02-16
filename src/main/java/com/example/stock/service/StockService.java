package com.example.stock.service;

import com.example.stock.entity.Company;
import com.example.stock.entity.StockHistory;
import com.example.stock.repository.CompanyRepository;
import com.example.stock.repository.StockHistoryRepository;
import com.example.stock.dto.StockResponseDto;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {
    private final CompanyRepository companyRepository;
    private final StockHistoryRepository stockHistoryRepository;

    public StockService(CompanyRepository companyRepository, StockHistoryRepository stockHistoryRepository) {
        this.companyRepository = companyRepository;
        this.stockHistoryRepository = stockHistoryRepository;
    }

    public List<StockResponseDto> getStockPrices(String companyCode, LocalDate startDate, LocalDate endDate) {
        Company company = companyRepository.findByCompanyCode(companyCode)
                .orElseThrow(() -> new IllegalArgumentException("not found Company: " + companyCode));

        List<StockHistory> stockHistories = stockHistoryRepository.findByCompanyCodeAndTradeDateBetween(companyCode, startDate, endDate);

        return stockHistories.stream()
                .map(stock -> new StockResponseDto(
                        company.getCompanyName(),
                        stock.getTradeDate().toString(),
                        stock.getClosePrice()
                ))
                .collect(Collectors.toList());
    }
}
