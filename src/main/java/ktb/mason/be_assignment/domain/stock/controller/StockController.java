package ktb.mason.be_assignment.domain.stock.controller;

import ktb.mason.be_assignment.domain.stock.controller.port.StockService;
import ktb.mason.be_assignment.domain.stock.controller.response.StockInfoResponse;
import ktb.mason.be_assignment.global.api.BaseResponse;
import ktb.mason.be_assignment.global.validate.ApiKey;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stocks")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockController {

    final StockService stockService;

    @ApiKey
    @GetMapping("")
    public BaseResponse<StockInfoResponse> getStockInfoByCompanyCode(
            @RequestParam(value = "company_code") String companyCode,
            @RequestParam(value = "start_date") String startDate,
            @RequestParam(value = "end_date") String endDate
    ) {

        return BaseResponse.success(
                stockService.getStockInfoByCompanyCode(companyCode, startDate, endDate)
        );
    }
}
