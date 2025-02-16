package org.sep2.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "company") // 테이블 이름 지정
@Getter
@Setter
@NoArgsConstructor
public class Company {

    @Id
    @Column(name = "company_code", nullable = false, length = 10)
    private String companyCode;


    @Column(name = "company_name", nullable = false)
    private String companyName;


}