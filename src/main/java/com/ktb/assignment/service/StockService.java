package com.ktb.assignment.service;

import com.ktb.assignment.domain.StocksHistory;
import com.ktb.assignment.dto.stock.response.StockInfoResponseDto;
import com.ktb.assignment.repository.StocksHistoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StockService {

    private final StocksHistoryRepository stocksHistoryRepository;

    public List<StockInfoResponseDto> getStockHistory(String companyCode, LocalDate startDate, LocalDate endDate){

        List<StocksHistory> stocksHistories = stocksHistoryRepository.findByCompany_CompanyCodeAndTradeDateBetween(companyCode, startDate, endDate);

        return stocksHistories.stream()
                .map(stocksHistory -> new StockInfoResponseDto(
                        stocksHistory.getCompany().getCompanyName(),
                        stocksHistory.getTradeDate().toString(),
                        stocksHistory.getClosePrice()
                ))
                .collect(Collectors.toList());
    }
}
