package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 기업(Company) 엔터티
 * - 기업 정보를 저장하는 테이블과 매핑
 * - 기업 코드(종목 코드)와 기업명을 포함
 */
@Entity
@Table(name = "company") // 테이블 `company`와 매핑
@Getter
@NoArgsConstructor
public class Company {

    /**
     * 기업 코드 (Primary Key)
     * - 최대 10자 (VARCHAR(10))
     * - `company` 테이블의 `company_code` 컬럼과 매핑
     */
    @Id
    @Column(name = "company_code", length = 10, nullable = false)
    private String companyCode;  

    /**
     * 기업명
     * - 최대 100자 (VARCHAR(100))
     * - `company` 테이블의 `company_name` 컬럼과 매핑
     */
    @Column(name = "company_name", length = 100, nullable = false)
    private String companyName;
}