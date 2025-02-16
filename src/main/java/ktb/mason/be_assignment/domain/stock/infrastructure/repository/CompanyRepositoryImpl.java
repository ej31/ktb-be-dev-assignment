package ktb.mason.be_assignment.domain.stock.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import ktb.mason.be_assignment.domain.stock.domain.Company;
import ktb.mason.be_assignment.domain.stock.infrastructure.entity.CompanyEntity;
import ktb.mason.be_assignment.domain.stock.service.port.CompanyRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static ktb.mason.be_assignment.domain.stock.infrastructure.entity.QCompanyEntity.companyEntity;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyRepositoryImpl implements CompanyRepository {

    final JPAQueryFactory query;

    @Override
    public Optional<Company> findByCompanyCode(String companyCode) {

        return Optional.ofNullable(query.select(companyEntity)
                .from(companyEntity)
                .where(companyEntity.companyCode.eq(companyCode))
                .fetchOne())
                .map(CompanyEntity::toModel);
    }
}
