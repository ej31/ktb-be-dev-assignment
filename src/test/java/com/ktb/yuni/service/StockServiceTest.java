package com.ktb.yuni.service;

import com.ktb.yuni.domain.Company;
import com.ktb.yuni.domain.StocksHistory;
import com.ktb.yuni.dto.StockResponseDto;
import com.ktb.yuni.repository.CompanyRepository;
import com.ktb.yuni.repository.StocksHistoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class StockServiceTest {
    @Autowired
    private StockService stockService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private StocksHistoryRepository stocksHistoryRepository;

    @Test
    @DisplayName("주식 데이터 조회 정상 응답 테스트")
    void getStockPrices_success() {
        // given
        Company company = new Company("YUNI", "유니회사");
        companyRepository.save(company);
        stocksHistoryRepository.save(new StocksHistory("YUNI", LocalDate.of(2025, 2, 1), 100f));

        // when
        StockResponseDto result = stockService.getStockPrices("YUNI", LocalDate.of(2025, 2, 1), LocalDate.of(2025, 2, 3));

        // then
        assertThat(result.getCompanyName()).isEqualTo("유니회사");
        assertThat(result.getStocks()).hasSize(1);
        assertThat(result.getStocks().get(0).getClosingPrice()).isEqualTo(100f);
    }

    @Test
    @DisplayName("존재하지 않는 회사 코드로 조회 시 예외 발생 테스트")
    void getStockPrices_companyNotFound() {
        // when
        Exception exception = assertThrows(RuntimeException.class, () ->
                stockService.getStockPrices("INVALID", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 3)));

        // then
        assertThat(exception.getMessage()).contains("회사 코드 'INVALID'를 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("startDate가 endDate보다 이후일 때 예외 발생 테스트")
    void getStockPrices_invalidDateRange() {
        // when
        Exception exception = assertThrows(RuntimeException.class, () ->
                stockService.getStockPrices("AAPL", LocalDate.of(2020, 1, 5), LocalDate.of(2020, 1, 3)));

        // then
        assertThat(exception.getMessage()).contains("startDate 는 endDate 보다 이후일 수 없습니다.");
    }

}
