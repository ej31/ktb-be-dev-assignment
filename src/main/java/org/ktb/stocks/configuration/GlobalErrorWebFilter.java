package org.ktb.stocks.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ktb.stocks.dto.ApiResponse;
import org.springframework.boot.web.reactive.filter.OrderedWebFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class GlobalErrorWebFilter implements OrderedWebFilter {
    private final ObjectMapper objectMapper;
    private static final int ORDER = -1000;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange)
                .onErrorResume(NoResourceFoundException.class, e -> {
                    exchange.getResponse().setStatusCode(e.getStatusCode());
                    return writeResponse(exchange, "Not Found");
                })
                .onErrorResume(ResponseStatusException.class, e -> {
                    exchange.getResponse().setStatusCode(e.getStatusCode());
                    log.debug("Exception", e);
                    return writeResponse(exchange, e.getReason());
                });
    }

    private Mono<Void> writeResponse(ServerWebExchange exchange, String message) {
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        ApiResponse jsonResponse = ApiResponse.failed(message);

        try {
            Flux<DataBuffer> dataBufferFlux = Mono.just(objectMapper.writeValueAsBytes(jsonResponse))
                    .map(bytes -> exchange.getResponse().bufferFactory().wrap(bytes))
                    .flux();
            return exchange.getResponse().writeWith(dataBufferFlux);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    @Override
    public int getOrder() {
        return ORDER;
    }
}