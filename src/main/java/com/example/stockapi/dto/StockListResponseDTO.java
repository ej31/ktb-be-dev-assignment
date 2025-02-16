package com.example.stockapi.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "stocks") // XML 루트 요소 지정
@XmlAccessorType(XmlAccessType.FIELD)
public class StockListResponseDTO {

    @XmlElement(name = "stock") // 개별 요소
    private List<StockResponseDTO> stocks;
}
