package com.hyun.stockapi.stock_api.service;

import com.hyun.stockapi.stock_api.dto.StockResponseDto;
import com.hyun.stockapi.stock_api.entity.Company;
import com.hyun.stockapi.stock_api.entity.StocksHistory;
import com.hyun.stockapi.stock_api.repository.CompanyRepository;
import com.hyun.stockapi.stock_api.repository.StocksHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class StockService {

    private final CompanyRepository companyRepository;
    private final StocksHistoryRepository stocksHistoryRepository;

    public StockService(CompanyRepository companyRepository, StocksHistoryRepository stocksHistoryRepository){
        this.companyRepository = companyRepository;
        this.stocksHistoryRepository = stocksHistoryRepository;
    }

    public List<StockResponseDto> getStockPrices(String companyCode, Locale tradeDate){
        Company company =companyRepository.findById(companyCode).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기업 코드: "+ companyCode));

        List<StocksHistory> historyList = stocksHistoryRepository.findByCompanyCodeAndTradeDateBetween(companyCode,tradeDate);

        return historyList.stream()
                .map(h -> StockResponseDto.builder()
                        .companyName(company.getCompanyName())
                        .tradeDate(h.getTradeDate().toString())
                        .closingPrice((long) h.getClosePrice())
                        .build())
                .toList();
    }
}
