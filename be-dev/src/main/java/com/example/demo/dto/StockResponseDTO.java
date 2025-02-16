package com.example.demo.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlElement;



/**
 * 주식 조회 응답 DTO
 * - API 응답에서 반환되는 주식 데이터 객체
 * - 특정 기업의 종가(closingPrice) 및 거래 날짜(tradeDate)를 포함
 */
@Getter
@NoArgsConstructor // XML 변환을 위해 기본 생성자 필요
@AllArgsConstructor
@XmlRootElement(name = "stock") // XML 직렬화
@XmlAccessorType(XmlAccessType.FIELD)
public class StockResponseDTO {
    @XmlElement(name = "companyName")  // XML에서 이 필드 포함
    private String companyName;   // 응답 시 회사명 포함
    
    @XmlElement(name = "tradeDate")
    private String tradeDate;   // yyyy-MM-dd 형식 유지
    
    @XmlElement(name = "closingPrice")
    private long closingPrice;   // 종가 (tradePrice)
}