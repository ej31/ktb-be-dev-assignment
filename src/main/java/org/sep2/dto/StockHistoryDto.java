package org.sep2.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class StockHistoryDto {
    private LocalDate tradeDate;
    private float openPrice;
    private float highPrice;
    private float lowPrice;
    private float closePrice;
    private float volume;
}