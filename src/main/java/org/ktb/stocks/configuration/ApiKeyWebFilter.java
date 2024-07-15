package org.ktb.stocks.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ktb.stocks.dto.ApiResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ApiKeyWebFilter implements WebFilter {
    private final ApiKeyProperty apiKeyProperty;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String apiKey = exchange.getRequest().getHeaders().getFirst(apiKeyProperty.getHeader());
        if (apiKey == null) {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            log.debug("Missing api key");
            return writeResponse(exchange, "Missing or invalid API Key");
        }

        if (!apiKeyProperty.getSecrets().contains(apiKey)) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            log.debug("Invalid api key : {}", apiKey);
            return writeResponse(exchange, "Missing or invalid API Key");
        }

        return chain.filter(exchange);
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
