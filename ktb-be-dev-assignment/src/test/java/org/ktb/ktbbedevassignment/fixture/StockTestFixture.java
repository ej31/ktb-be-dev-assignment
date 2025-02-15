package org.ktb.ktbbedevassignment.fixture;

import java.time.LocalDate;

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
}
