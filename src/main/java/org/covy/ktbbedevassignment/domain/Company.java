package org.covy.ktbbedevassignment.domain;

import jakarta.persistence.*;
import lombok.*;

// DB 테이블과 매핑

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class Company {

    @Id
    @Column(name = "company_code", length = 10, nullable = false)
    private String companyCode;  // PRIMARY KEY (기본키)

    @Column(name = "company_name", length = 100, nullable = false)
    private String companyName;
}

