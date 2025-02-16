package org.covy.ktbbedevassignment.service;

import org.covy.ktbbedevassignment.domain.Company;
import org.covy.ktbbedevassignment.domain.StockHistory;
import org.covy.ktbbedevassignment.repository.StockHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StockHistoryService {
    private final StockHistoryRepository stockHistoryRepository;

    public StockHistory saveStockHistory(Company company, LocalDate tradeDate, float openPrice, float highPrice, float lowPrice, float closePrice, float volume) {
        StockHistory stockHistory = new StockHistory(company, tradeDate, openPrice, highPrice, lowPrice, closePrice, volume);
        return stockHistoryRepository.save(stockHistory);
    }
}

