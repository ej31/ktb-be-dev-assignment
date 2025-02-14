package ktb.back.assignment.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseCompanyDto {
    private String companyName;
    private String tradeDate;
    private float closingPrice;
}
