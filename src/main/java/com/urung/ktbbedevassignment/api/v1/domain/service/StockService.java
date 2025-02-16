package com.urung.ktbbedevassignment.api.v1.domain.service;

import com.urung.ktbbedevassignment.api.v1.domain.dto.StockPriceDto;
import com.urung.ktbbedevassignment.domain.company.entity.Company;
import com.urung.ktbbedevassignment.domain.company.repository.CompanyRepository;
import com.urung.ktbbedevassignment.domain.stocksHistory.entity.StocksHistory;
import com.urung.ktbbedevassignment.domain.stocksHistory.repository.StocksHistoryRepository;
import com.urung.ktbbedevassignment.global.exception.BadRequestException;
import com.urung.ktbbedevassignment.global.exception.ErrorResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.urung.ktbbedevassignment.global.exception.ErrorResponseStatus.*;


@Slf4j
@Service
public class StockService {
    private final CompanyRepository companyRepository;
    private final StocksHistoryRepository stocksHistoryRepository;

    public StockService(CompanyRepository companyRepository, StocksHistoryRepository stocksHistoryRepository) {
        this.companyRepository = companyRepository;
        this.stocksHistoryRepository = stocksHistoryRepository;
    }

    public List<StockPriceDto.StockPriceResponse> getStockPrices(StockPriceDto.StockPriceRequest request){
        String companyName = companyRepository.findCompanyNameByCode(request.getCompanyCode())
                .orElseThrow(() -> new BadRequestException(COMPANY_NOT_FOUND));

        List<StocksHistory> stockHistories = stocksHistoryRepository
                .findByCompanyCodeAndTradeDateBetween(
                        request.getCompanyCode(),
                        request.getStartDate(),
                        request.getEndDate()
                );

        // DTO 변환
        return stockHistories.stream()
                .map(history -> {
                    StockPriceDto.StockPriceResponse res = new StockPriceDto.StockPriceResponse();
                    res.setCompanyName(companyName);
                    res.setTradeDate(history.getTradeDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    res.setClosingPrice(history.getClosePrice());
                    return res;
                }).collect(Collectors.toList());
    }
}