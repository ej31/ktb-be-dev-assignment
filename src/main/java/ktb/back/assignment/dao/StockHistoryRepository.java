package ktb.back.assignment.dao;

import ktb.back.assignment.model.ResponseCompanyDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class StockHistoryRepository {
    private final JdbcTemplate jdbcTemplate;

    public StockHistoryRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ResponseCompanyDto> findCompanyStockHistory(String companyCode, Date startDate, Date endDate){
        String sql = """
                SELECT c.company_name , s.trade_date, s.close_price
                FROM company c
                JOIN stocks_history s ON c.company_code = s.company_code
                WHERE c.company_code = ? AND s.trade_date BETWEEN ? AND ?
                ORDER BY s.trade_date
                """;

        return jdbcTemplate.query(sql,(rs, rowNum) -> new ResponseCompanyDto(
                rs.getString("company_name"),
                rs.getString("trade_date"),
                rs.getFloat("close_price")
        ), companyCode,startDate,endDate);
    }
}
