package org.sep2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Setter
@Getter
public class StockHistoryRequestDto {

    // Getter & Setter
    @NotBlank(message = "companyCode는 필수 입력값입니다.")  // 빈 값 허용 안 함
    private String companyCode;

    @NotNull(message = "startDate는 필수 입력값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")  // 날짜 형식 지정
    private LocalDate startDate;

    @NotNull(message = "endDate는 필수 입력값입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;


    private String apikey;

    // 기본 생성자
    public StockHistoryRequestDto() {}

    // 생성자
    public StockHistoryRequestDto(String companyCode, LocalDate startDate, LocalDate endDate, String apikey) {
        this.companyCode = companyCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.apikey = apikey;
    }

}
