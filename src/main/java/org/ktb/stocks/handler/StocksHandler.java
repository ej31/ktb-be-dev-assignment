package org.ktb.stocks.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ktb.stocks.dto.ApiResponse;
import org.ktb.stocks.dto.ClosingPriceDTO;
import org.ktb.stocks.dto.ClosingPriceResult;
import org.ktb.stocks.service.StocksService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class StocksHandler {
    private final StocksService stocksService;

    public Mono<ServerResponse> getClosingPriceBetweenDates(ServerRequest request) {
        Flux<ClosingPriceDTO> closingPrices = stocksService.getClosingPriceBetweenDates(request);

        Mono<ApiResponse> response = closingPrices
                .map(dto -> new ClosingPriceResult(dto.companyName(), dto.tradeDate(), dto.closePrice()))
                .collectList()
                .map(ApiResponse::success);

        return ServerResponse.ok().body(response, ApiResponse.class);
    }


}
