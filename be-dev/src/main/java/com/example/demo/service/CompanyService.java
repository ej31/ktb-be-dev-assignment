package com.example.demo.service;

import com.example.demo.entity.Company;
import com.example.demo.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    // 생성자 주입 (Spring이 자동으로 CompanyRepository를 주입)
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // 모든 기업 정보 조회
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    // 특정 기업 조회
    public Optional<Company> getCompanyByCode(String companyCode) {
        return companyRepository.findById(companyCode);
    }
}