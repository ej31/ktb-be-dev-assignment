package org.covy.ktbbedevassignment.service;

import org.covy.ktbbedevassignment.domain.Company;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CompanyServiceTest {

    @Autowired
    private CompanyService companyService;

    @Test
    public void testCreateCompany() {
        Company company = companyService.createCompany("AAPL", "Apple Inc.");
        Assertions.assertNotNull(company.getCompanyCode());
        System.out.println("✅ 저장된 기업: " + company.getCompanyName());
    }
}

