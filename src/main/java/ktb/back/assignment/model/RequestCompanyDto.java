package ktb.back.assignment.model;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RequestCompanyDto {
    private String company_code;
    private LocalDate startDate;
    private LocalDate endDate;
}
