package org.ktb.stocks.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Getter
@RequiredArgsConstructor
public class ClosingPriceResponse {
    private final Flux<?> prices;
}
