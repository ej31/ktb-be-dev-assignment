package com.example.demo.dto;

import com.example.demo.dto.StockResponseDTO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "stocks")
public class StockListResponseDTO {
    @XmlElement(name = "stock")
    private List<StockResponseDTO> stocks;

    public StockListResponseDTO() {} // 기본 생성자 필요

    public StockListResponseDTO(List<StockResponseDTO> stocks) {
        this.stocks = stocks;
    }
}