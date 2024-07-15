package org.ktb.stocks.controller;

import lombok.RequiredArgsConstructor;
import org.ktb.stocks.domain.Company;
import org.ktb.stocks.domain.StocksHistory;
import org.ktb.stocks.repository.CompanyRepository;
import org.ktb.stocks.repository.StocksHistoryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final CompanyRepository companyRepository;
    private final StocksHistoryRepository stocksHistoryRepository;

    @GetMapping("/company")
    public Flux<Company> test() {
        return companyRepository.findAll();
    }

    @GetMapping("/stocks")
    public Flux<StocksHistory> testHistory() {
        return stocksHistoryRepository.findAll(100);
    }
}
