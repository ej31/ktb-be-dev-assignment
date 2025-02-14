package ktb.mason.be_assignment.domain.stock.controller.response;

import ktb.mason.be_assignment.domain.stock.domain.Company;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyResponse {
    final String code;
    final String name;

    public static CompanyResponse from(Company company) {
        return CompanyResponse.builder()
                .code(company.getCompanyCode())
                .name(company.getCompanyName())
                .build();
    }
}
