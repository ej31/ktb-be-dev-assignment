package org.ktb.stock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StockRequestDto {
    @JsonProperty("company_code")
    private String companyCode;

    @JsonProperty("start_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String startDate;

    @JsonProperty("end_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String endDate;
}
