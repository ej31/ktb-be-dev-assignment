package com.jenny.ktb_be_dev_assignment.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
public class StockHistoryId implements Serializable {
    private String companyCode;
    private LocalDate tradeDate;
}
