package com.example.backend.service;

import com.example.backend.entity.StocksHistory;
import com.example.backend.repository.StockHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockHistoryService {
    private final StockHistoryRepository stockHistoryRepository;

    public List<StocksHistory> getStockPrices(String companyCode, LocalDate startDate, LocalDate endDate) {
        return stockHistoryRepository.findByCompanyCodeAndTradeDateBetween(companyCode, startDate, endDate);
    }
}