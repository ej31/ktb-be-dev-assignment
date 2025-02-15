package ktb.mason.be_assignment.domain.stock.service;

import ktb.mason.be_assignment.domain.stock.controller.port.StockService;
import ktb.mason.be_assignment.domain.stock.controller.response.StockInfoResponse;
import ktb.mason.be_assignment.domain.stock.domain.Company;
import ktb.mason.be_assignment.domain.stock.domain.StockHistory;
import ktb.mason.be_assignment.domain.stock.domain.StockInfo;
import ktb.mason.be_assignment.domain.stock.service.port.CompanyRepository;
import ktb.mason.be_assignment.domain.stock.service.port.StockHistoryRepository;
import ktb.mason.be_assignment.global.api.ApiException;
import ktb.mason.be_assignment.global.api.AppHttpStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Builder
@RequiredArgsConstructor
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StockServiceImpl implements StockService {

    final StockHistoryRepository stockHistoryRepository;
    final CompanyRepository companyRepository;
    @Override
    @Transactional(readOnly = true)
    public StockInfoResponse getStockInfoByCompanyCode(String companyCode, String startDate, String endDate) {

        Company company = companyRepository.findByCompanyCode(companyCode)
                .orElseThrow(() -> new ApiException(AppHttpStatus.NOT_FOUND_COMPANY));

        List<StockHistory> stockHistories = stockHistoryRepository.findAllByCompanyAndDateRange(companyCode, startDate, endDate);

        return StockInfoResponse.from(StockInfo.builder()
                .company(company)
                .stockHistories(stockHistories)
                .build());
    }
}
