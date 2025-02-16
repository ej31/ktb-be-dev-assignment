package com.example.stockApi.service;

import com.example.stockApi.dto.stock.StockHistoryResponse;
import com.example.stockApi.entity.StocksHistoryEntity;
import com.example.stockApi.exception.ApiException;
import com.example.stockApi.repository.stock.CompanyRepository;
import com.example.stockApi.repository.stock.StocksHistoryRepository;
import com.example.stockApi.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockHistoryService {

    private final StocksHistoryRepository stocksHistoryRepository;
    private final CompanyRepository companyRepository;

    @Autowired
    public StockHistoryService(StocksHistoryRepository stocksHistoryRepository, CompanyRepository companyRepository) {
        this.stocksHistoryRepository = stocksHistoryRepository;
        this.companyRepository = companyRepository;
    }

    public List<StockHistoryResponse> getStockHistory(String companyCode, LocalDate startDate, LocalDate endDate){
        boolean exists = companyRepository.findByCompanyCode(companyCode).isPresent();
        if(!exists){
            throw new ApiException(ErrorCode.COMPANY_NOT_FOUND);
        }

        List<StockHistoryResponse> stockHistoryEntities = stocksHistoryRepository.findStockHistoryWithCompanyName(companyCode, startDate, endDate);

        return stockHistoryEntities;
    }

}