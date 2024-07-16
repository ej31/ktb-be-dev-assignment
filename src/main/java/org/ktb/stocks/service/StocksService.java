package org.ktb.stocks.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ktb.stocks.configuration.StringLocalDateConverter;
import org.ktb.stocks.dto.ClosingPriceDTO;
import org.ktb.stocks.repository.StocksHistoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Service
public class StocksService {
    private final StringLocalDateConverter stringLocalDateConverter;
    private final StocksHistoryRepository stocksHistoryRepository;

    public Flux<ClosingPriceDTO> getClosingPriceBetweenDates(ServerRequest request) {

        String companyCode = request.pathVariable("companyCode");

        try {
            LocalDate startDate = request.queryParam("startDate")
                    .map(stringLocalDateConverter::convert)
                    .orElseThrow(() -> new IllegalArgumentException("startDate is required"));
            LocalDate endDate = request.queryParam("endDate")
                    .map(stringLocalDateConverter::convert)
                    .orElseThrow(() -> new IllegalArgumentException("endDate is required"));
            if (startDate.isAfter(endDate)) {
                throw new IllegalArgumentException("startDate must be before endDate");
            }

            log.debug("{} {} {}", companyCode, startDate, endDate);

            return stocksHistoryRepository.closingPriceBetweenDates(companyCode, startDate, endDate);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
