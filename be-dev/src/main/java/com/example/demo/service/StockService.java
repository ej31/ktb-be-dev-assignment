package com.example.demo.service;

import com.example.demo.dto.StockResponseDTO;
import com.example.demo.entity.StocksHistory;
import com.example.demo.repository.StockHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockService {

    private final StockHistoryRepository stockHistoryRepository;

    public StockService(StockHistoryRepository stockHistoryRepository) {
        this.stockHistoryRepository = stockHistoryRepository;
    }

    /**
     * 특정 기업의 주가 데이터를 조회
     * @param companyCode 조회할 기업 코드
     * @param startDate 조회 시작 날짜
     * @param endDate 조회 종료 날짜
     * @return `StockResponseDTO` 리스트 (해당 날짜 범위 내 데이터)
     */
    public List<StockResponseDTO> getStockPrices(String companyCode, LocalDate startDate, LocalDate endDate) {
        List<StocksHistory> stockList = stockHistoryRepository.findByCompanyCodeAndTradeDateBetween(companyCode, startDate, endDate);

        return stockList.stream()
            .map(stock -> new StockResponseDTO(
                stock.getCompanyCode(), // 기업 코드 반환
                stock.getTradeDate().toString(), // 날짜 변환 (yyyy-MM-dd)
                (long) stock.getClosingPrice() // 종가 변환 float → long
            ))
            .collect(Collectors.toList());
    }
}