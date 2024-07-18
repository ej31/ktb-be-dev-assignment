package kcs.kcsdev.stock.repository;

import java.util.Optional;
import kcs.kcsdev.stock.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

      Optional<Company> findByCompanyCode(String companyCoed);
}
