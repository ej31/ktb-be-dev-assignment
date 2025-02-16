package org.sep2.service.impl;

import org.sep2.domain.StockHistory;
import org.sep2.dto.StockHistoryResponseDto;
import org.sep2.repository.StockHistoryRepository;
import org.sep2.service.StockHistoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockHistoryServiceImpl implements StockHistoryService {

    private final StockHistoryRepository stockHistoryRepository;

    public StockHistoryServiceImpl(StockHistoryRepository stockHistoryRepository) {
        this.stockHistoryRepository = stockHistoryRepository;
    }

    @Override
    public List<StockHistoryResponseDto> getStockHistory(String companyCode, LocalDate startDate, LocalDate endDate) {
        List<StockHistory> stockHistoryList =
                stockHistoryRepository.findByIdCompanyCodeAndIdTradeDateBetween(companyCode, startDate, endDate);

        return stockHistoryList.stream()
                .map(stockHistory -> new StockHistoryResponseDto(
                        stockHistory.getCompany().getCompanyName(),
                        stockHistory.getId().getTradeDate(),
                        (long) stockHistory.getClosePrice()
                ))
                .collect(Collectors.toList());
    }
}
