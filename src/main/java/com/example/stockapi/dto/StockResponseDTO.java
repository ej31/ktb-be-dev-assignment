package com.example.stockapi.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "stock") // XML 지원을 위해 추가
@XmlAccessorType(XmlAccessType.FIELD) // JAXB 필드 접근
public class StockResponseDTO {
    @XmlElement
    private String companyName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @XmlElement
    private LocalDate tradeDate;

    @XmlElement
    private Float openPrice;
    @XmlElement
    private Float highPrice;
    @XmlElement
    private Float lowPrice;
    @XmlElement
    private Float closingPrice;
    @XmlElement
    private Float volume;
}
