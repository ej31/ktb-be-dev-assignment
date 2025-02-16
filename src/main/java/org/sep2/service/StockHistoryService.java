package org.sep2.service;

import org.sep2.dto.StockHistoryResponseDto;
import java.time.LocalDate;
import java.util.List;

public interface StockHistoryService {
    List<StockHistoryResponseDto> getStockHistory(String companyCode, LocalDate startDate, LocalDate endDate);
}
