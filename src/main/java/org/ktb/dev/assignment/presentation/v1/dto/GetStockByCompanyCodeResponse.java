package org.ktb.dev.assignment.presentation.v1.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@JsonRootName("stockData")
@Schema(name="GetStockByCompanyCodeResponse", description ="기업 별 기간 내 주식 정보 조회 결과")
public record GetStockByCompanyCodeResponse(
        @Schema(description = "기업명", requiredMode = Schema.RequiredMode.REQUIRED)
        String companyName,

        @JacksonXmlElementWrapper(localName = "stockPrices")
        @Schema(description = "주가 정보 목록", requiredMode = Schema.RequiredMode.REQUIRED)
        List<StockPriceInfo> stockPrices
) {
        @JsonRootName("stockPrice")
        public record StockPriceInfo(
                @Schema(description = "거래일", requiredMode = Schema.RequiredMode.REQUIRED)
                String tradeDate,

                @Schema(description = "가격", requiredMode = Schema.RequiredMode.REQUIRED)
                float closingPrice
        ) {}
}