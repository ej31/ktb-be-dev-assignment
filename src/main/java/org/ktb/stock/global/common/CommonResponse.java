package org.ktb.stock.global.common;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;
import org.ktb.stock.global.error.code.ErrorCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@XmlRootElement(name = "CommonResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonResponse<T>{
    private boolean success; // 요청의 성공 여부를 나타내기 위함
    private String message; // 사용자나 개발자가 확인할 수 있는 설명을 제공하기 위함
    private T data; // 데이터를 실제로 담는 필드

    public static <T> ResponseEntity<CommonResponse<T>> success(T data, MediaType mediaType) {
        String message = (data instanceof List && ((List<?>) data).isEmpty())
                ? "해당 기간에 조회된 주식 데이터가 없습니다."
                : "주식 데이터를 성공적으로 조회했습니다.";
        CommonResponse<T> response = CommonResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(response);
    }

    public static <T> ResponseEntity<CommonResponse<T>> error(ErrorCode errorCode, MediaType mediaType) {
        CommonResponse<T> response = CommonResponse.<T>builder()
                .success(false)
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getStatus())
                .contentType(mediaType)
                .body(response);
    }
}
