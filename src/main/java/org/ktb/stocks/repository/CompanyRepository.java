package org.ktb.stocks.repository;

import org.ktb.stocks.domain.Company;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

public interface CompanyRepository extends R2dbcRepository<Company, String> {
    @Query("SELECT * FROM company")
    Flux<Company> findAll();
}
