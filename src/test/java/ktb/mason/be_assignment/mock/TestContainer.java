package ktb.mason.be_assignment.mock;

import ktb.mason.be_assignment.domain.stock.controller.port.StockService;
import ktb.mason.be_assignment.domain.stock.service.StockServiceImpl;
import lombok.Builder;

public class TestContainer {
    public final FakeStockHistoryRepository fakeStockHistoryRepository;
    public final FakeCompanyRepository fakeCompanyRepository;
    public final StockService stockService;

    @Builder
    public TestContainer() {
        fakeStockHistoryRepository = new FakeStockHistoryRepository();
        fakeCompanyRepository = new FakeCompanyRepository();
        this.stockService = StockServiceImpl.builder()
                .stockHistoryRepository(fakeStockHistoryRepository)
                .companyRepository(fakeCompanyRepository)
                .build();
    }
}
