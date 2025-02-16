package com.example.demo.controller;

import com.example.demo.entity.Company;
import com.example.demo.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 기업 정보 조회 컨트롤러
 * - 기업 목록 및 특정 기업 정보 조회 기능 제공
 * - API 요청 시 API Key 인증이 필요 (Interceptor에서 처리됨)
 */
@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService companyService;

    /**
     * CompanyService 의존성 주입 (생성자 방식)
     * @param companyService 기업 정보를 제공하는 서비스
     */
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * 모든 기업 목록 조회 API
     * @return 기업 목록 (List<Company>)
     */
    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAllCompanies());
    }

    /**
     * 특정 기업 정보 조회 API
     * @param companyCode 조회할 기업 코드 (예: "AAPL")
     * @return 해당 기업 정보 또는 404 Not Found
     */
    @GetMapping("/{companyCode}")
    public ResponseEntity<Company> getCompanyByCode(@PathVariable String companyCode) {
        Optional<Company> company = companyService.getCompanyByCode(companyCode);
        return company.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}