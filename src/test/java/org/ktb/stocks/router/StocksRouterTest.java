package org.ktb.stocks.router;

import org.junit.jupiter.api.Test;
import org.ktb.stocks.configuration.ApiKeyProperty;
import org.ktb.stocks.configuration.BucketConfiguration;
import org.ktb.stocks.dto.ApiResponse;
import org.ktb.stocks.dto.ClosingPriceResponse;
import org.ktb.stocks.service.BucketService;
import org.ktb.stocks.service.StocksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureWebTestClient
class StocksRouterTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ApiKeyProperty apiKeyProperty;

    @Test
    void closing_price_will_return_400() {
        String companyName = "NVDA";
        webClient.get()
                .uri("/v1/companies/{}/price/closing", companyName)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.BAD_REQUEST)
                .expectBody()
                .jsonPath("$.message")
                .isEqualTo("Missing Api Key");
    }

    @Test
    void closing_price_will_return_403() {
        String companyName = "NVDA";
        given(apiKeyProperty.getHeader()).willReturn("x-api-key");
        given(apiKeyProperty.getSecrets()).willReturn(List.of("api-secret"));

        webClient.get()
                .uri("/v1/companies/{}/price/closing", companyName)
                .header(apiKeyProperty.getHeader(), "wrong-secret")
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.FORBIDDEN)
                .expectBody().jsonPath("$.message")
                .isEqualTo("Invalid Api Key");
    }

    @Test
    void closing_price_success() {
        String companyName = "NVDA";
        given(apiKeyProperty.getHeader()).willReturn("x-api-key");
        given(apiKeyProperty.getSecrets()).willReturn(List.of("api-secret"));

        webClient.get()
                .uri("/v1/companies/{companyName}/price/closing?startDate={startDate}&endDate={endDate}", companyName, "20240101", "20240130")
                .header(apiKeyProperty.getHeader(), apiKeyProperty.getSecrets().getFirst())
                .exchange()

                .expectStatus()
                .isOk()

                .expectBody()
                .jsonPath("$.message")
                .isEqualTo("OK")

                .jsonPath("$.data")
                .isArray();
    }
}