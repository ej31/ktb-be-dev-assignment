package org.ktb.stocks.router;

import org.ktb.stocks.handler.StocksHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class StocksRouter {
    @Bean
    public RouterFunction<ServerResponse> routing(StocksHandler handler) {
        return route(GET("/v1/companies/{companyCode}/price/closing"), handler::getClosingPriceBetweenDates);
    }
}
