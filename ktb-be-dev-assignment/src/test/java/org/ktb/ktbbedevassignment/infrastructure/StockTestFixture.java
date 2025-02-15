package org.ktb.ktbbedevassignment.infrastructure;

import java.time.LocalDate;

public class StockTestFixture {

    // 비교할 회사가 3개 이상된다면 동적으로 생성하는 방법을 고려해볼 수 있음
    public static final String TEST_COMPANY_CODE = "TEST CO";
    public static final String TEST_COMPANY_NAME = "TEST COM";

    public static final String TEST_OTHER_COMPANY_CODE = "OTHER CO";
    public static final String TEST_OTHER_COMPANY_NAME = "OTHER COM";

    public static final String TEST_TRADE_DATE = "2025-02-01";

    public static final float TEST_OPENING_PRICE = 150.0f;

    public static final float TEST_HIGHEST_PRICE = 155.0f;

    public static final float TEST_LOWEST_PRICE = 148.0f;

    public static final float TEST_CLOSING_PRICE = 152.5f;

    public static final float TEST_VOLUME = 1000000;

    public static String plusDay(String date, int day) {
        return LocalDate.parse(date).plusDays(day).toString();
    }
}
