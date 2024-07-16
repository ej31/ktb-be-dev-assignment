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

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ApiKeyWebFilter implements OrderedWebFilter {
    private final ApiKeyProperty apiKeyProperty;
    private static final int ORDER = -1;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Optional<String> optionalKey = retrieveApiKey(exchange);

        String apiKey = optionalKey.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing Api Key"));

        if (!apiKeyProperty.getSecrets().contains(apiKey)) {
            log.debug("Invalid api key : {}", apiKey);
            return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Api Key"));
        }

        return chain.filter(exchange);
    }

    private Optional<String> retrieveApiKey(ServerWebExchange exchange) {
        String apiKeyInHeader = exchange.getRequest().getHeaders().getFirst(apiKeyProperty.getHeader());
        if (apiKeyInHeader != null) {
            return apiKeyInHeader.describeConstable();
        }

        String apiKeyInQuery = exchange.getRequest().getQueryParams().getFirst(apiKeyProperty.getQuery());
        if (apiKeyInQuery != null) {
            return apiKeyInQuery.describeConstable();
        }

        return Optional.empty();
    }


    @Override
    public int getOrder() {
        return ORDER;
    }
}
