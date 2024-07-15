package org.ktb.stocks.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ktb.stocks.dto.ApiResponse;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Order(-2)
@Component
public class GlobalWebExceptionHandler implements WebExceptionHandler {
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof NoResourceFoundException noResourceFoundException) {
            exchange.getResponse().setStatusCode(noResourceFoundException.getStatusCode());
            return writeResponse(exchange, "Not Found");
        }
        if (ex instanceof ResponseStatusException responseStatusException) {
            exchange.getResponse().setStatusCode(responseStatusException.getStatusCode());
            return writeResponse(exchange, (responseStatusException.getReason()));
        }
        return writeResponse(exchange, ex.getMessage());
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
}