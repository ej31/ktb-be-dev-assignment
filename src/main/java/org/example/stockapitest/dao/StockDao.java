package org.example.stockapitest.dao;

import org.example.stockapitest.dto.StockDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StockDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StockDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<StockDto> getStockInfo(String companyCode, String startDate, String endDate) {
        String sql = "SELECT c.company_name, sh.trade_date, sh.close_price " +
                "FROM company AS c JOIN stocks_history AS sh " +
                "ON c.company_code = sh.company_code " +
                "WHERE c.company_code = ? " +
                "AND sh.trade_date BETWEEN ? AND ?";

        return jdbcTemplate.query(sql, new Object[]{companyCode, startDate, endDate}, new StockMapper());
    }

    private static class StockMapper implements RowMapper<StockDto> {
        @Override
        public StockDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            StockDto stockDto = new StockDto();
            stockDto.setCompany_name(rs.getString("company_name"));
            stockDto.setTrade_date(rs.getString("trade_date"));
            stockDto.setTrade_price(rs.getLong("close_price"));
            return stockDto;
        }
    }
}
