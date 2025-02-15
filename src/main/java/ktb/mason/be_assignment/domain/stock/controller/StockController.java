package ktb.mason.be_assignment.domain.stock.controller;

import ktb.mason.be_assignment.domain.stock.controller.port.StockService;
import ktb.mason.be_assignment.domain.stock.controller.response.StockInfoResponse;
import ktb.mason.be_assignment.global.api.BaseResponse;
import ktb.mason.be_assignment.global.validate.ApiKey;
import ktb.mason.be_assignment.global.validate.Code;
import ktb.mason.be_assignment.global.validate.Date;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stocks")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockController {

    final StockService stockService;

    @ApiKey
    @GetMapping("")
    public BaseResponse<StockInfoResponse> getStockInfoByCompanyCode(
            @Code @RequestParam(value = "company_code") String companyCode,
            @Date @RequestParam(value = "start_date") String startDate,
            @Date @RequestParam(value = "end_date") String endDate
    ) {

        return BaseResponse.success(
                stockService.getStockInfoByCompanyCode(companyCode, startDate, endDate)
        );
    }
}
