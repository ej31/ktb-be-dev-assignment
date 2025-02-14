package ktb.mason.be_assignment.domain.stock.service;

import ktb.mason.be_assignment.domain.stock.controller.port.StockService;
import ktb.mason.be_assignment.domain.stock.controller.response.StockInfoResponse;
import ktb.mason.be_assignment.domain.stock.service.port.StockRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockServiceImpl implements StockService {

    final StockRepository stockRepository;

    @Override
    @Transactional(readOnly = true)
    public StockInfoResponse getStockInfoByCompanyCode(String companyCode, String startDate, String endDate) {
        return StockInfoResponse.from(stockRepository.findAllByCompanyCode(companyCode, startDate, endDate));
    }
}
