package org.ktb.stocks.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ktb.stocks.configuration.StringLocalDateConverter;
import org.ktb.stocks.dto.ApiResponse;
import org.ktb.stocks.dto.ClosingPriceDTO;
import org.ktb.stocks.dto.ClosingPriceResponse;
import org.ktb.stocks.dto.ClosingPriceResult;
import org.ktb.stocks.repository.StocksHistoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class StocksHandler {
    private final StringLocalDateConverter stringLocalDateConverter;
    private final StocksHistoryRepository stocksHistoryRepository;

    public Mono<ServerResponse> getClosingPriceBetweenDates(ServerRequest request) {

        String companyCode = request.pathVariable("companyCode");
        LocalDate startDate = request.queryParam("startDate")
                .map(stringLocalDateConverter::convert)
                .orElseThrow(() -> new IllegalArgumentException("startDate is required"));
        LocalDate endDate = request.queryParam("endDate")
                .map(stringLocalDateConverter::convert)
                .orElseThrow(() -> new IllegalArgumentException("endDate is required"));

        log.debug("{} {} {}", companyCode, startDate, endDate);

        Flux<ClosingPriceDTO> closingPrices = stocksHistoryRepository.closingPriceBetweenDates(companyCode, startDate, endDate);

        Mono<ApiResponse> response = closingPrices
                .map(dto -> new ClosingPriceResult(dto.companyName(), dto.tradeDate(), dto.closePrice()))
                .collectList()
                .map(ApiResponse::success);

        return ServerResponse.ok().body(response, ApiResponse.class);
    }


}
