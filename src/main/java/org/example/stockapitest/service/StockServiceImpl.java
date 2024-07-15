package org.example.stockapitest.service;

import org.example.stockapitest.dao.StockDao;
import org.example.stockapitest.dto.StockDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    private final StockDao stockDao;

    @Autowired
    public StockServiceImpl(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    @Override
    public List<StockDto> getStockInfo(String companyCode, String startDate, String endDate) {
        return stockDao.getStockInfo(companyCode, startDate, endDate);
    }
}
