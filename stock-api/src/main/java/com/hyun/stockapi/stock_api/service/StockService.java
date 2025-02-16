package com.hyun.stockapi.stock_api.service;

import com.hyun.stockapi.stock_api.dto.StockResponseDto;

import com.hyun.stockapi.stock_api.entity.StocksHistory;
import com.hyun.stockapi.stock_api.repository.StocksHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class StockService {

    private final StocksHistoryRepository stocksHistoryRepository;

    public StockService(StocksHistoryRepository stocksHistoryRepository){
        this.stocksHistoryRepository = stocksHistoryRepository;
    }

    public List<StockResponseDto> getStockPricesByRange(String companyCode, LocalDate startDate, LocalDate endDate ){
        List<StocksHistory> historyList = stocksHistoryRepository
                .findByCompanyCodeAndTradeDateBetween(companyCode, startDate, endDate);

        return historyList.stream()
                .map(h -> new StockResponseDto(
                        h.getCompany().getCompanyName(),
                        h.getTradeDate().toString(),
                        h.getClosePrice()
                ))
                .toList();
    }

}
