package org.ktb.dev.assignment.presentation.v1.dto;

import java.time.LocalDate;

public record StockPriceDto(
        LocalDate tradeDate,
        float closePrice
) {}
