package com.judycompany.assignment1.v1.service;

import com.judycompany.assignment1.v1.model.StockHistory;
import com.judycompany.assignment1.v1.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    public List<StockHistory> getStockHistory(String companyCode, LocalDate startDate, LocalDate endDate) {
        return stockRepository.findByCompanyCodeAndTradeDateBetween(companyCode, startDate, endDate);
    }
}
