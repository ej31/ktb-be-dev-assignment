package ktb.back.assignment.controller;

import ktb.back.assignment.dto.ResponseCompanyStockHistory;
import ktb.back.assignment.global.validator.RequestValidator;
import ktb.back.assignment.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/company")
public class CompanyController {
    private final CompanyService companyService;
    private final RequestValidator requestValidator;

    public CompanyController(CompanyService companyService,RequestValidator requestValidator){
        this.companyService = companyService;
        this.requestValidator = requestValidator;
    }

    @GetMapping
    public List<ResponseCompanyStockHistory> getCompanyPrice(
            @RequestHeader(value = "x-api-key", required = false) String apiKeyHeader,
            @RequestParam(required = false) String companyCode,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        requestValidator.validateApiKey(apiKeyHeader);
        requestValidator.validateRequestParams(companyCode,startDate,endDate);

        return companyService.getCompanyStockHistory(companyCode, startDate, endDate);
    }
}
