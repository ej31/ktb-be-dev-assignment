package org.ktb.stocks.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@RequiredArgsConstructor
public class Company {
    @Id
    @Column("company_code")
    private final String companyCode;
    @Column("company_name")
    private final String companyName;
}
