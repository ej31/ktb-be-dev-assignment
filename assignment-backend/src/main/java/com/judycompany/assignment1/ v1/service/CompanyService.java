package com.judycompany.assignment1.v1.service;

import com.judycompany.assignment1.v1.model.Company;
import com.judycompany.assignment1.v1.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}
