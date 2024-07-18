package kcs.kcsdev.stock.service.impl;

import java.time.LocalDate;
import java.util.List;
import kcs.kcsdev.global.exception.GlobalException;
import kcs.kcsdev.global.exception.type.ErrorCode;
import kcs.kcsdev.stock.dto.CompanyResponse;
import kcs.kcsdev.stock.dto.StocksHistoryResponse;
import kcs.kcsdev.stock.entity.Company;
import kcs.kcsdev.stock.entity.StocksHistory;
import kcs.kcsdev.stock.repository.CompanyRepository;
import kcs.kcsdev.stock.repository.StockHistoryRepository;
import kcs.kcsdev.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

      private final CompanyRepository companyRepository;
      private final StockHistoryRepository stockHistoryRepository;


      @Override
      public CompanyResponse getStockHistory(String companyCode, LocalDate startDate,
              LocalDate endDate) {

            Company company = companyRepository.findByCompanyCode(companyCode).orElseThrow(
                    () -> new GlobalException(ErrorCode.COMPANY_NOT_FOUND));

            List<StocksHistory> histories = stockHistoryRepository
                    .findByCompanyCompanyCodeAndTradeDateBetween(companyCode, startDate, endDate)
                    .orElseThrow(() -> new GlobalException(ErrorCode.HISTORY_NOT_FOUND));

            List<StocksHistoryResponse> stocksHistoryResponses = histories.stream()
                    .map(StocksHistoryResponse::fromEntity).toList();

            return CompanyResponse.fromEntityWithHistories(company, stocksHistoryResponses);
      }


}
