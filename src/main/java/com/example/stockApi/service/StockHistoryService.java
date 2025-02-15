package com.example.stockApi.service;

import com.example.stockApi.dto.stock.StockHistoryResponse;
import com.example.stockApi.entity.StocksHistoryEntity;
import com.example.stockApi.repository.stock.StocksHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockHistoryService {

    private final StocksHistoryRepository stocksHistoryRepository;

    @Autowired
    public StockHistoryService(StocksHistoryRepository stocksHistoryRepository) {
        this.stocksHistoryRepository = stocksHistoryRepository;
    }

    public List<StocksHistoryEntity> getStockHistory(String companyCode, LocalDate startDate, LocalDate endDate){
        return stocksHistoryRepository.findByCompanyCodeAndTradeDateBetween(companyCode, startDate, endDate);
    }

}