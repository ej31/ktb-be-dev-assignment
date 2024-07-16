package org.ktb.stocks.configuration;

import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.ktb.stocks.service.BucketService;
import org.springframework.boot.web.reactive.filter.OrderedWebFilter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ApiThrottlingWebFilter implements OrderedWebFilter {
    private final ApiKeyProperty apiKeyProperty;
    private final BucketService bucketService;
    private static final int ORDER = -2;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String apiKey = exchange.getRequest().getHeaders().getFirst(apiKeyProperty.getHeader());
        Bucket bucket = bucketService.resolveBucket(apiKey);

        if (bucket.tryConsume(1)) {
            return chain.filter(exchange);
        }

        throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests");
    }

    @Override
    public int getOrder() {
        return ORDER;
    }
}
