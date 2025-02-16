package com.jenny.ktb_be_dev_assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class StockResponse {
    private String message;
    private List<Data> data;

    @Getter
    @AllArgsConstructor
    public static class Data {
        private String companyName;
        private String tradeDate;
        private long closingPrice;
    }
}
