package org.ktb.ktbbedevassignment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record StockInfoRequest(
        @NotBlank(message = "companyCode는 필수입니다.")
        @Size(max = 10, message = "companyCode는 10자 이하여야 합니다.")
        String companyCode,

        @NotBlank(message = "startDate는 필수입니다.")
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "startDate는 yyyy-MM-dd 형식이어야 합니다.")
        String startDate,

        @NotBlank(message = "endDate는 필수입니다.")
        @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "endDate는 yyyy-MM-dd 형식이어야 합니다.")
        String endDate
) {
}
