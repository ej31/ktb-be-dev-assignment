package org.ktb.ktbbedevassignment.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import static org.ktb.ktbbedevassignment.fixture.StockTestFixture.TEST_CLOSING_PRICE;
import static org.ktb.ktbbedevassignment.fixture.StockTestFixture.TEST_HIGHEST_PRICE;
import static org.ktb.ktbbedevassignment.fixture.StockTestFixture.TEST_LOWEST_PRICE;
import static org.ktb.ktbbedevassignment.fixture.StockTestFixture.TEST_OPENING_PRICE;
import static org.ktb.ktbbedevassignment.fixture.StockTestFixture.TEST_VOLUME;

@Component
public class TestDataHelper {

    private final JdbcTemplate jdbcTemplate;

    public TestDataHelper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void clearStockHistory() {
        jdbcTemplate.execute("DELETE FROM stocks_history");
    }

    public void clearAllTables() {
        clearStockHistory();
        jdbcTemplate.execute("DELETE FROM company");
    }

    public void insertCompany(String companyCode, String companyName) {
        String sql = "INSERT INTO company (company_code, company_name) VALUES (?, ?)";
        jdbcTemplate.update(sql, companyCode, companyName);
    }

    public void insertStockHistory(String companyCode, String tradeDate, float open, float high, float low, float close, float volume) {
        String sql = "INSERT INTO stocks_history (company_code, trade_date, open_price, high_price, low_price, close_price, volume) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, companyCode, tradeDate, open, high, low, close, volume);
    }

    public void insertStockHistory(String companyCode, String tradeDate) {
        insertStockHistory(companyCode, tradeDate, TEST_OPENING_PRICE, TEST_HIGHEST_PRICE, TEST_LOWEST_PRICE, TEST_CLOSING_PRICE, TEST_VOLUME);
    }
}
