package ktb.back.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ResponseCompanyStockHistory {
    private String companyName;
    private LocalDate tradeDate;
    private float closingPrice;
}
