package org.ktb.ktbbedevassignment.fixture;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.ktb.ktbbedevassignment.dto.StockInfoDto;
import static org.ktb.ktbbedevassignment.fixture.CompanyTestFixture.TEST_COMPANY_CODE;

public class StockTestFixture {

    public static final String TEST_TRADE_DATE = "2025-02-01";

    public static final float TEST_OPENING_PRICE = 150.0f;

    public static final float TEST_HIGHEST_PRICE = 155.0f;

    public static final float TEST_LOWEST_PRICE = 148.0f;

    public static final float TEST_CLOSING_PRICE = 152.5f;

    public static final float TEST_VOLUME = 1000000;

    public static String plusDay(String date, int day) {
        return LocalDate.parse(date).plusDays(day).toString();
    }

    public static StockInfoDto createTestStockInfoDto(String companyName, String tradeDate, float closingPrice) {
        return new StockInfoDto(companyName, tradeDate, closingPrice);
    }

    public static List<StockInfoDto> createTestStockInfoDtoList(int size) {
        List<StockInfoDto> stockInfoDtoList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            stockInfoDtoList.add(createTestStockInfoDto(TEST_COMPANY_CODE, plusDay(TEST_TRADE_DATE, i), TEST_CLOSING_PRICE));
        }
        return stockInfoDtoList;
    }
}
