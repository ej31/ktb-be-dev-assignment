package org.ktb.ktbbedevassignment.infrastructure;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepository {

    private final JdbcTemplate jdbcTemplate;

    public CompanyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean existsByCompanyCode(String companyCode) {
        String sql = "SELECT COUNT(*) FROM company WHERE company_code = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, companyCode);
        return count != null && count > 0;
    }
}
