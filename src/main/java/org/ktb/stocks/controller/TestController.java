package org.ktb.stocks.controller;

import lombok.RequiredArgsConstructor;
import org.ktb.stocks.domain.Company;
import org.ktb.stocks.repository.CompanyRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@RestController
public class TestController {
    private final CompanyRepository companyRepository;

    @GetMapping("/")
    public Flux<Company> test() {
        return companyRepository.findAll();
    }
}
