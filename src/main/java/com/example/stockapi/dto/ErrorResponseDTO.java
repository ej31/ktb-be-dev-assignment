package com.example.stockapi.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "errorResponse") // XML 변환 지원
@XmlAccessorType(XmlAccessType.FIELD) // JAXB 필드 접근
public class ErrorResponseDTO {
    @XmlElement
    private String error;
    @XmlElement
    private String message;
}
