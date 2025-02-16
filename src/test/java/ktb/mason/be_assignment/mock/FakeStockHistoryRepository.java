package ktb.mason.be_assignment.mock;

import ktb.mason.be_assignment.domain.stock.domain.StockHistory;
import ktb.mason.be_assignment.domain.stock.service.port.StockHistoryRepository;

import java.time.LocalDate;
import java.util.*;

public class FakeStockHistoryRepository implements StockHistoryRepository {
	private final List<StockHistory> data = Collections.synchronizedList(new ArrayList<>());

    public StockHistory save(StockHistory stockHistory) {
        data.removeIf(item -> Objects.equals(item.getCompanyCode(), stockHistory.getCompanyCode()));
        data.add(stockHistory);
        return stockHistory;
    }

    public List<StockHistory> saveAll(List<StockHistory> stockHistories) {
        stockHistories.forEach(stockHistory ->
                data.removeIf(item -> Objects.equals(item.getCompanyCode(), stockHistory.getCompanyCode()))
        );
        // 새 데이터 추가
        data.addAll(stockHistories);

        return stockHistories;
    }

    @Override
    public List<StockHistory> findAllByCompanyAndDateRange(String companyCode, String startDate, String endDate) {
        return data.stream()
                .filter(s -> byCompanyCodeAndDate(companyCode, startDate, endDate, s))
                .toList();
    }

    private boolean byCompanyCodeAndDate(String companyCode, String startDate, String endDate, StockHistory stockHistory) {
        if(companyCode == null || startDate == null || endDate == null) return false;

        LocalDate s = LocalDate.parse(startDate);
        LocalDate e = LocalDate.parse(endDate);

        return stockHistory.getCompanyCode().equals(companyCode)
                && !stockHistory.getTradeDate().isBefore(s)
                && !stockHistory.getTradeDate().isAfter(e);
    }


}