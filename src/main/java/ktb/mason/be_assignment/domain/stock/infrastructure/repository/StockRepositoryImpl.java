package ktb.mason.be_assignment.domain.stock.infrastructure.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ktb.mason.be_assignment.domain.stock.domain.Company;
import ktb.mason.be_assignment.domain.stock.domain.StockHistory;
import ktb.mason.be_assignment.domain.stock.domain.StockInfo;
import ktb.mason.be_assignment.domain.stock.service.port.StockRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static ktb.mason.be_assignment.domain.stock.infrastructure.entity.QCompanyEntity.companyEntity;
import static ktb.mason.be_assignment.domain.stock.infrastructure.entity.QStockHistoryEntity.stockHistoryEntity;

@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockRepositoryImpl implements StockRepository {

    final JPAQueryFactory query;

    @Override
    public StockInfo findAllByCompanyCode(String companyCode, String startDate, String endDate) {

        List<Tuple> results = query
                .select(stockHistoryEntity, companyEntity)
                .from(stockHistoryEntity)
                .join(companyEntity)
                .on(stockHistoryEntity.pk.companyCode.eq(companyEntity.companyCode))
                .where(byCompanyCodeAndDate(companyCode, startDate, endDate))
                .fetch();

        // 조회 결과가 없는 경우 null 반환
        if (results.isEmpty()) {
            return null;
        }

        Company company = results.get(0).get(companyEntity).toModel();

        List<StockHistory> stockHistories = results.stream()
                .map(tuple -> tuple.get(stockHistoryEntity).toModel())
                .toList();

        return StockInfo.builder()
                .company(company)
                .stocks(stockHistories)
                .build();
    }

    BooleanExpression byCompanyCodeAndDate(String companyCode, String startDate, String endDate) {
        if(companyCode == null || startDate == null || endDate == null) return null;

        LocalDate s = LocalDate.parse(startDate);
        LocalDate e = LocalDate.parse(endDate);

        return stockHistoryEntity.pk.companyCode.eq(companyCode)
                .and(stockHistoryEntity.pk.tradeDate.goe(s))
                .and(stockHistoryEntity.pk.tradeDate.loe(e));
    }
}
