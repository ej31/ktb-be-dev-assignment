package ktb.mason.be_assignment.domain.stock.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Company {
    final String companyCode;
    final String companyName;

    @Builder
    public Company(String companyCode, String companyName) {
        this.companyCode = companyCode;
        this.companyName = companyName;
    }
}
