package ktb.back.assignment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestCompanyStockHistory {
    @NotNull(message = "companyCode 파라미터가 필요합니다.")
    private String companyCode;

    @NotNull(message = "startDate 파라미터가 필요합니다.")
    private String startDate;

    @NotNull(message = "endDate 파라미터가 필요합니다.")
    private String endDate;
}
