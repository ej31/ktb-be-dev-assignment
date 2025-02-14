package com.hyun.stockapi.stock_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "company")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @Column(name ="company_code", nullable = false, unique = true)
    private String companyCode;

    @Column(name ="company_name", nullable= false)
    private String companyName;

}
