package kcs.kcsdev.stock.service;

import java.time.LocalDate;
import kcs.kcsdev.stock.dto.CompanyResponse;

public interface StockService {

      CompanyResponse getStockHistory(String companyCode, LocalDate startDate, LocalDate endDate);
}
