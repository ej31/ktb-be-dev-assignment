package ktb.back.assignment.repository;

import ktb.back.assignment.dto.ResponseCompanyStockHistory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class StockHistoryRepositoryImpl implements StockHistoryRepository{
    private final JdbcTemplate jdbcTemplate;

    public StockHistoryRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ResponseCompanyStockHistory> findCompanyStockHistory(String companyCode, LocalDate startDate, LocalDate endDate){
        String sql = """
                SELECT c.company_name , s.trade_date, s.close_price
                FROM company c
                JOIN stocks_history s ON c.company_code = s.company_code
                WHERE c.company_code = ? AND s.trade_date BETWEEN ? AND ?
                ORDER BY s.trade_date
                """;

        return jdbcTemplate.query(sql,companyStockRowMapper(), companyCode,startDate,endDate);
    }

    private RowMapper<ResponseCompanyStockHistory> companyStockRowMapper(){
        return (rs,rowNum) -> new ResponseCompanyStockHistory(
                rs.getString("company_name"),
                rs.getDate("trade_date").toLocalDate(),
                rs.getFloat("close_price")
        );
    }
}
