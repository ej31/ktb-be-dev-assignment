package org.ktb.stock.repository;

import lombok.RequiredArgsConstructor;
import org.ktb.stock.dto.StockResponseDto;
import org.ktb.stock.dto.StockServiceDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StockRepository {
    private final JdbcTemplate jdbcTemplate;

    // 원하는 반환 결과 값으로 매핑
    private final RowMapper<StockResponseDto> stockResponseDtoRowMapper = (rs, rowNum) ->
            new StockResponseDto(
                    rs.getString("company_name"),
                    rs.getDate("trade_date").toLocalDate(),
                    rs.getFloat("close_price")
            );

    // 사용자가 입력한 회사 코드, 조회 시작 기간, 조회 종료 기간에 해당하는 주식 정보 가져오는 로직
    public List<StockResponseDto> findStocks(StockServiceDto stockServiceDto) {
        String sql = "SELECT c.company_name, sh.trade_date, sh.close_price FROM company c " +
                "JOIN stocks_history sh " +
                "ON c.company_code = sh.company_code " +
                "WHERE c.company_code = ? AND sh.trade_date BETWEEN ? AND ?";

        return jdbcTemplate.query(
                sql,
                stockResponseDtoRowMapper,
                stockServiceDto.getCompanyCode(),
                stockServiceDto.getStartDate(),
                stockServiceDto.getEndDate()
        );
    }

    // 입력 받은 회사코드가 있는 회사인지 확인
    public boolean findCompany(String companyCode) {
        String sql = "SELECT company_name FROM company WHERE company_code = ?";

        jdbcTemplate.queryForObject(sql, String.class, companyCode);

        return true;
    }
}
