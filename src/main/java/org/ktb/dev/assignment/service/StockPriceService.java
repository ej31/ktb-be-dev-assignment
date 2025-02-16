package org.ktb.dev.assignment.service;

import org.ktb.dev.assignment.core.exception.BusinessException;
import org.ktb.dev.assignment.core.exception.CustomErrorCode;
import org.ktb.dev.assignment.presentation.v1.dto.GetStockByCompanyCodeResponse;
import org.ktb.dev.assignment.presentation.v1.dto.StockPriceDto;
import org.ktb.dev.assignment.repository.CompanyRepository;
import org.ktb.dev.assignment.repository.StocksHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockPriceService {
    private final CompanyRepository companyRepository;
    private final StocksHistoryRepository stocksHistoryRepository;

    public StockPriceService(
            CompanyRepository companyRepository,
            StocksHistoryRepository stocksHistoryRepository) {
        this.companyRepository = companyRepository;
        this.stocksHistoryRepository = stocksHistoryRepository;
    }

    public GetStockByCompanyCodeResponse getStockByCompany(String companyCode, LocalDate startDate, LocalDate endDate) {
        String companyName = companyRepository.findCompanyNameByCode(companyCode)
                .orElseThrow(() -> new BusinessException(
                        CustomErrorCode.COMPANY_NOT_FOUND,
                        String.format("기업 코드 %s를 찾을 수 없습니다.", companyCode)
                ));

        List<StockPriceDto> stockPriceDtoList =  stocksHistoryRepository
                .findStockPricesByCompanyAndDateRange(companyCode, startDate, endDate);

        List<GetStockByCompanyCodeResponse.StockPriceInfo> stockPriceInfos = stockPriceDtoList.stream()
                .map(sp -> new GetStockByCompanyCodeResponse.StockPriceInfo(
                        sp.tradeDate().toString(),
                        sp.closePrice()
                ))
                .toList();

        return new GetStockByCompanyCodeResponse(companyName, stockPriceInfos);
    }
}
