package kcs.kcsdev.stock.web;

import java.time.LocalDate;
import kcs.kcsdev.stock.dto.CompanyResponse;
import kcs.kcsdev.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController {

      private final StockService stockService;

      @GetMapping
      public ResponseEntity<?> test() {
            return ResponseEntity.ok("test");
      }


      //성공적인 응답의 경우, DTO 클래스에 데이터를 래핑하여 일관된 응답 포맷을 사용
      @GetMapping("/history")
      public ResponseEntity<CompanyResponse> getStocksHistory(
              @RequestParam("companyCode") String companyCode,
              @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
              @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
            return ResponseEntity.ok(stockService.getStockHistory(companyCode, startDate, endDate));
      }


}
