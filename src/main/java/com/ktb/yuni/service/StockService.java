package com.ktb.yuni.service;

import com.ktb.yuni.domain.Company;
import com.ktb.yuni.domain.StocksHistory;
import com.ktb.yuni.dto.StockResponseDto;
import com.ktb.yuni.exception.CompanyNotFoundException;
import com.ktb.yuni.repository.CompanyRepository;
import com.ktb.yuni.repository.StocksHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockService {
    private final CompanyRepository companyRepository;
    private final StocksHistoryRepository stocksHistoryRepository;

    public StockService(CompanyRepository companyRepository, StocksHistoryRepository stocksHistoryRepository) {
        this.companyRepository = companyRepository;
        this.stocksHistoryRepository = stocksHistoryRepository;
    }

    public StockResponseDto getStockPrices(String companyCode, LocalDate startDate, LocalDate endDate) {
        // 회사 조회
        Company company = companyRepository.findByCompanyCode(companyCode)
                .orElseThrow(()->new CompanyNotFoundException(companyCode));

        // 주식 데이터 조회
        List<StocksHistory> stocks = stocksHistoryRepository
                .findByCompanyCodeAndTradeDateBetween(companyCode, startDate, endDate);

        // DTO로 변환
        List<StockResponseDto.StockDetail> stockDetails = stocks.stream()
                .map(stock -> new StockResponseDto.StockDetail(stock.getTradeDate().toString(), stock.getClosePrice()))
                .toList();

        return new StockResponseDto(company.getCompanyName(), stockDetails);
    }
}
