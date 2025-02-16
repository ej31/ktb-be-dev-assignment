package com.jenny.ktb_be_dev_assignment.service;

import com.jenny.ktb_be_dev_assignment.dto.StockResponse;
import com.jenny.ktb_be_dev_assignment.entity.StockHistory;
import com.jenny.ktb_be_dev_assignment.repository.StockHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockHistoryService {

    private final StockHistoryRepository stockHistoryRepository;

    public StockHistoryService(StockHistoryRepository stockHistoryRepository) {
        this.stockHistoryRepository = stockHistoryRepository;
    }

    public List<StockResponse.Data> getStockHistory(String companyCode, LocalDate startDate, LocalDate endDate) {
        List<StockHistory> stocks = stockHistoryRepository.findByCompanyCodeAndTradeDateBetweenOrderByTradeDate(
                companyCode, startDate, endDate
        );

        // stocks 리스트를 StockResponse.Data 객체 리스트로 변환
        return stocks.stream()
                .map(stock -> new StockResponse.Data(
                        stock.getCompany().getCompanyName(),
                        stock.getTradeDate().toString(),
                        Math.round(stock.getClosePrice())
                ))
                .collect(Collectors.toList());
    }
}
