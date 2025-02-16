package org.covy.ktbbedevassignment.service;

import org.covy.ktbbedevassignment.domain.Company;
import org.covy.ktbbedevassignment.domain.StockHistory;
import org.covy.ktbbedevassignment.repository.CompanyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class StockHistoryServiceTest {

    @Autowired
    private StockHistoryService stockHistoryService;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    public void testSaveStockHistory() {
        Company company = companyRepository.findById("AAPL").orElse(new Company("AAPL", "Apple Inc."));
        StockHistory stock = stockHistoryService.saveStockHistory(
                company,
                LocalDate.of(2024, 2, 16),
                180.5f, 185.0f, 178.0f, 183.0f, 1000000
        );

        Assertions.assertNotNull(stock);
        System.out.println("✅ 저장된 주식 데이터: " + stock.getTradeDate() + " 종가: " + stock.getClosePrice());
    }
}

