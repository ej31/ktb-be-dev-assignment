package ktb.mason.be_assignment.domain.stock.infrastructure.entity;

import jakarta.persistence.*;
import ktb.mason.be_assignment.domain.stock.domain.Company;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "company")
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyEntity {

    @Id
    @Column(name = "company_code")
    String companyCode;

    @Column(name = "company_name")
    String companyName;

    @Builder
    public CompanyEntity(String companyCode, String companyName) {
        this.companyCode = companyCode;
        this.companyName = companyName;
    }

    public static CompanyEntity from(Company company) {
        return CompanyEntity.builder()
                .companyCode(company.getCompanyCode())
                .companyName(company.getCompanyName())
                .build();
    }

    public Company toModel() {
        return Company.builder()
                .companyCode(this.companyCode)
                .companyName(this.companyName)
                .build();
    }
}
