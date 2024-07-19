package com.judycompany.assignment1.v1.controller;

import com.judycompany.assignment1.v1.model.Company;
import com.judycompany.assignment1.v1.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    private static final String API_KEY = "c18aa07f-f005-4c2f-b6db-dff8294e6b5e";

    @GetMapping("/companies")
    public ResponseEntity<?> getCompanies(@RequestParam(required = false) String apikey,
                                          @RequestHeader(value = "x-api-key", required = false) String apiKeyHeader) {

        String apiKey = apikey != null ? apikey : apiKeyHeader;

        if (apiKey == null || !API_KEY.equals(apiKey)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Company> companies = companyService.getAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }
}
