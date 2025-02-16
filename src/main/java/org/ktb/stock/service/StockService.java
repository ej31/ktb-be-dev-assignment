package org.ktb.stock.service;

import lombok.RequiredArgsConstructor;
import org.ktb.stock.dto.StockResponseDto;
import org.ktb.stock.dto.StockSearchDto;
import org.ktb.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {
    private final StockRepository stockRepository;

    public List<StockResponseDto> getStocks(StockSearchDto stockSearchDto) {
        return stockRepository.findStocks(stockSearchDto);
    }

    public boolean getCompany(String companyCode) {
        return stockRepository.findCompany(companyCode);
    }
}
