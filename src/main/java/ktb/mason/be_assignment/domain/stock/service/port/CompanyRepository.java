package ktb.mason.be_assignment.domain.stock.service.port;

import ktb.mason.be_assignment.domain.stock.domain.Company;

import java.util.Optional;

public interface CompanyRepository {
    Optional<Company> findByCompanyCode(String companyCode);
}
