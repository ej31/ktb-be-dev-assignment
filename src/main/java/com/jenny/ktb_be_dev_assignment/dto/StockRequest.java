package com.jenny.ktb_be_dev_assignment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockRequest {
    private String companyCode;
    private String startDate;
    private String endDate;
}
