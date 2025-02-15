package ktb.mason.be_assignment.mock;

import ktb.mason.be_assignment.domain.stock.domain.Company;
import ktb.mason.be_assignment.domain.stock.service.port.CompanyRepository;

import java.util.*;

public class FakeCompanyRepository implements CompanyRepository {
	private final List<Company> data = Collections.synchronizedList(new ArrayList<>());

    public Company save(Company company) {
        data.removeIf(item -> Objects.equals(item.getCompanyCode(), company.getCompanyCode()));
        data.add(company);
        return company;
    }

    @Override
    public Optional<Company> findByCompanyCode(String companyCode) {
        return data.stream()
                .filter(c -> c.getCompanyCode().equals(companyCode))
                .findAny();
    }
}