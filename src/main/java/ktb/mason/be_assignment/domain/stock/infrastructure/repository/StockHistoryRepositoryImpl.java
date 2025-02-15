package ktb.mason.be_assignment.domain.stock.infrastructure.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import ktb.mason.be_assignment.domain.stock.domain.StockHistory;
import ktb.mason.be_assignment.domain.stock.infrastructure.entity.StockHistoryEntity;
import ktb.mason.be_assignment.domain.stock.service.port.StockHistoryRepository;
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
public class StockHistoryRepositoryImpl implements StockHistoryRepository {

    final JPAQueryFactory query;

    @Override
    public List<StockHistory> findAllByCompanyAndDateRange(String companyCode, String startDate, String endDate) {

        return query
                .select(stockHistoryEntity)
                .from(stockHistoryEntity)
                .join(companyEntity)
                .on(stockHistoryEntity.pk.companyCode.eq(companyEntity.companyCode))
                .where(byCompanyCodeAndDate(companyCode, startDate, endDate))
                .fetch()
                .stream()
                .map(StockHistoryEntity::toModel)
                .toList();
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
