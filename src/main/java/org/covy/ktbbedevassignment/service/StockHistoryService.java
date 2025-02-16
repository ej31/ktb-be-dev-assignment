package org.covy.ktbbedevassignment.service;

import lombok.RequiredArgsConstructor;
import org.covy.ktbbedevassignment.domain.Company;
import org.covy.ktbbedevassignment.domain.StockHistory;
import org.covy.ktbbedevassignment.dto.StockResponseDto;
import org.covy.ktbbedevassignment.repository.CompanyRepository;
import org.covy.ktbbedevassignment.repository.StockHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockHistoryService {

    private final StockHistoryRepository stockHistoryRepository;
    private final CompanyRepository companyRepository;

    // ✅ 주식 데이터 저장 메서드
    public StockHistory saveStockHistory(Company company, LocalDate tradeDate,
                                         float openPrice, float highPrice,
                                         float lowPrice, float closePrice,
                                         float volume) {
        StockHistory stockHistory = new StockHistory();
        stockHistory.setCompany(company);
        stockHistory.setTradeDate(tradeDate);
        stockHistory.setOpenPrice(openPrice);
        stockHistory.setHighPrice(highPrice);
        stockHistory.setLowPrice(lowPrice);
        stockHistory.setClosePrice(closePrice);
        stockHistory.setVolume(volume);

        return stockHistoryRepository.save(stockHistory);
    }

    // ✅ 주식 데이터 조회 메서드 추가 (컨트롤러에서 호출)
    public List<StockResponseDto> getStockHistory(String companyCode, LocalDate startDate, LocalDate endDate) {
        Company company = companyRepository.findById(companyCode)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회사 코드입니다: " + companyCode));

        List<StockHistory> stocks = stockHistoryRepository.findByCompanyCompanyCodeAndTradeDateBetween(companyCode, startDate, endDate);

        if (stocks.isEmpty()) {
            throw new IllegalArgumentException("해당 기간 동안의 주식 데이터가 없습니다.");
        }

        return stocks.stream()
                .map(stock -> StockResponseDto.builder()
                        .companyName(company.getCompanyName())
                        .tradeDate(stock.getTradeDate())
                        .closingPrice(stock.getClosePrice())
                        .build())
                .collect(Collectors.toList());
    }
}




