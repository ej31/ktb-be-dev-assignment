package org.ktb.stocks.dto;

import java.time.LocalDate;

public record ClosingPriceDTO(
        String companyName,
        LocalDate tradeDate,
        Long closePrice
) {
}
