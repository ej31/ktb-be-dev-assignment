package org.ktb.ktbbedevassignment.presentation;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import org.ktb.ktbbedevassignment.aop.JsonXmlResponse;
import org.ktb.ktbbedevassignment.application.ApiKeyValidator;
import org.ktb.ktbbedevassignment.application.StockService;
import org.ktb.ktbbedevassignment.dto.ApiResponse;
import org.ktb.ktbbedevassignment.dto.StockInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stocks")
public class StockController {

    private final StockService stockService;
    private final ApiKeyValidator apiKeyValidator;

    public StockController(StockService stockService, ApiKeyValidator apiKeyValidator) {
        this.stockService = stockService;
        this.apiKeyValidator = apiKeyValidator;
    }

    @GetMapping
    @JsonXmlResponse
    public ResponseEntity<ApiResponse<List<StockInfoDto>>> getStocks(
            @RequestParam String companyCode,
            @RequestParam String startDate,
            @RequestParam String endDate,
            HttpServletRequest request
    ) {
        String apiKey = Optional.ofNullable(request.getHeader("x-api-key"))
                .orElse(request.getParameter("apikey"));
        apiKeyValidator.validateApiKey(apiKey);

        List<StockInfoDto> result = stockService.getStockInfo(companyCode, startDate, endDate);
        ApiResponse<List<StockInfoDto>> response = ApiResponse.success(result);
        return ResponseEntity.status(response.status()).body(response);
    }
}
