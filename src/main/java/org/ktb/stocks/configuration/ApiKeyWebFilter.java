package org.ktb.stocks.configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.filter.OrderedWebFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ApiKeyWebFilter implements OrderedWebFilter {
    private final ApiKeyProperty apiKeyProperty;
    private static final int ORDER = -1;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String apiKey = exchange.getRequest().getHeaders().getFirst(apiKeyProperty.getHeader());
        if (apiKey == null) {
            log.debug("Missing api key");
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing Api Key"));
        }

        if (!apiKeyProperty.getSecrets().contains(apiKey)) {
            log.debug("Invalid api key : {}", apiKey);
            return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Api Key"));
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return ORDER;
    }
}
