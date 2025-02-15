package org.ktb.dev.assignment.presentation.v1.controller;

import jakarta.validation.constraints.Size;
import org.ktb.dev.assignment.core.response.SuccessResponse;
import org.ktb.dev.assignment.presentation.v1.dto.GetStockByCompanyCodeResponse;
import org.ktb.dev.assignment.service.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class StockPriceController implements StockPriceApi {
    private final StockPriceService stockPriceService;

    public StockPriceController(StockPriceService stockPriceService) {
        this.stockPriceService = stockPriceService;
    }

    @Override
    public ResponseEntity<SuccessResponse<GetStockByCompanyCodeResponse>> getStockByCompany(
            String companyCode, LocalDate startDate, LocalDate endDate) {
        return SuccessResponse.of(
                stockPriceService.getStockByCompany(companyCode, startDate, endDate)
        ).asHttp(HttpStatus.OK);
    }
}
