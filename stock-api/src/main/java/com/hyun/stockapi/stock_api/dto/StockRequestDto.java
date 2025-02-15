package com.hyun.stockapi.stock_api.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockRequestDto {

    private String apikey;

    @NotNull(message = "종목 코드(companyCode)는 필수 입력값입니다.")
    @Size(max = 10, message = "종목 코드는 최대 10자까지 가능합니다.")
    private String companyCode;

    @NotNull(message = "조회 날짜(tradeDate)는 필수 입력값입니다.")
    @Pattern(regexp="\\d{4}-\\d{2}-\\d{2}", message="날짜 형식이 올바르지 않습니다. (yyyy-MM-dd 형식으로 입력해주세요)")
    private String tradeDate;

}
