package com.ktb.yuni.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class StocksHistoryId implements Serializable {
    private String companyCode;
    private LocalDate tradeDate;
}
