package org.example.stockapitest.service;

import org.example.stockapitest.dto.StockDto;

import java.util.List;

public interface StockService {
    List<StockDto> getStockInfo(String companyCode, String startDate, String endDate);
}
