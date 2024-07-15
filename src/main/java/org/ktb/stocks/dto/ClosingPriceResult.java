package org.ktb.stocks.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class ClosingPriceResult {
    private final String companyName;
    private final LocalDate tradeDate;
    private final Long closingPrice;
}
