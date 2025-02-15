package org.ktb.ktbbedevassignment.infrastructure;

import org.ktb.ktbbedevassignment.dto.StockInfoDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StockRepository {

    private final JdbcTemplate jdbcTemplate;

    public StockRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<StockInfoDto> findStockInfoList(String companyCode, String startDate, String endDate) {
        String sql = """
                    SELECT c.company_name, s.trade_date, s.close_price
                    FROM stocks_history s
                    JOIN company c ON s.company_code = c.company_code
                    WHERE s.company_code = ? 
                    AND s.trade_date BETWEEN ? AND ?
                    ORDER BY s.trade_date
                """;

        RowMapper<StockInfoDto> stockRowMapper = (rs, rowNum) -> new StockInfoDto(
                rs.getString("company_name"),
                rs.getString("trade_date"),
                rs.getFloat("close_price")
        );

        return jdbcTemplate.query(sql, stockRowMapper, companyCode, startDate, endDate);
    }
}
