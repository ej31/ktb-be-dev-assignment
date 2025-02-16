package org.covy.ktbbedevassignment.service;

import org.covy.ktbbedevassignment.domain.Company;
import org.covy.ktbbedevassignment.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;

    public Company createCompany(String companyCode, String companyName) {
        Company company = new Company(companyCode, companyName);
        return companyRepository.save(company);
    }
}

