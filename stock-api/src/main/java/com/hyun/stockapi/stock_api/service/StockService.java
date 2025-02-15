package com.hyun.stockapi.stock_api.service;

import com.hyun.stockapi.stock_api.dto.StockResponseDto;
import com.hyun.stockapi.stock_api.entity.Company;
import com.hyun.stockapi.stock_api.entity.StocksHistory;
import com.hyun.stockapi.stock_api.repository.CompanyRepository;
import com.hyun.stockapi.stock_api.repository.CustomStocksHistoryRepository;
import com.hyun.stockapi.stock_api.repository.StocksHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
public class StockService {

    private final CustomStocksHistoryRepository customStocksHistoryRepository;
    private final StocksHistoryRepository stocksHistoryRepository;

    public StockService(StocksHistoryRepository stocksHistoryRepository, CustomStocksHistoryRepository customStocksHistoryRepository){
        this.customStocksHistoryRepository = customStocksHistoryRepository;
        this.stocksHistoryRepository = stocksHistoryRepository;
    }

    public List<StockResponseDto> getStockPrices(String companyCode, LocalDate tradeDate){
        return customStocksHistoryRepository.findStockPrices(companyCode,tradeDate);
    }
}
