package com.ktb.assignment.stock.service;

import com.ktb.assignment.stock.dto.*;
import com.ktb.assignment.stock.entiry.*;
import com.ktb.assignment.stock.exception.StockException;
import com.ktb.assignment.stock.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    private final CompanyRepository companyRepository;
    private final StocksHistoryRepository stocksHistoryRepository;

    public StockService(CompanyRepository companyRepository, StocksHistoryRepository stocksHistoryRepository) {
        this.companyRepository = companyRepository;
        this.stocksHistoryRepository = stocksHistoryRepository;
    }

    public List<StockHistoryResponse> getStockHistory(String companyCode, LocalDate startDate, LocalDate endDate) {
        Company company = companyRepository.findByCompanyCode(companyCode);
        if (company == null) {
            throw new StockException(404, "Company not found");
        }

        List<StocksHistory> historyList = stocksHistoryRepository.findById_CompanyCodeAndId_TradeDateBetween(companyCode, startDate, endDate);
        if (historyList.isEmpty()) {
            throw new StockException(404, "No stock history found");
        }

        List<StockHistoryResponse> responseList = new ArrayList<StockHistoryResponse>();

        for (StocksHistory history : historyList) {
            responseList.add(new StockHistoryResponse(
                    company.getCompanyName(),
                    history.getId().getTradeDate().toString(),
                    history.getClosePrice()
            ));
        }

        return responseList;
    }
}