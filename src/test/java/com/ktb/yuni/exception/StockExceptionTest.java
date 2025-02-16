package com.ktb.yuni.exception;

import com.ktb.yuni.dto.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StockExceptionTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String BASE_URL = "/api/v1/stocks";


    @Test
    @DisplayName("필수 파라미터 누락 시 400 Bad Request 테스트")
    void testMissingParameter() {
        // given - startDate 파라미터 누락
        String url = BASE_URL + "?companyCode=AAPL&endDate=2020-01-01&apiKey=c18aa07f-f005-4c2f-b6db-dff8294e6b5e";

        // when
        ResponseEntity<ApiResponse> response = restTemplate.getForEntity(url, ApiResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage()).isEqualTo("필수 파라미터 'startDate'이(가) 누락되었습니다.");
    }

    @Test
    @DisplayName("잘못된 날짜 형식 입력 시 400 Bad Request 테스트")
    void testInvalidDateFormat () {
        // given - 잘못된 날짜 형식
        String url = BASE_URL + "?companyCode=AAPL&startDate=2020-12-dd&endDate=2020-01-01&apiKey=c18aa07f-f005-4c2f-b6db-dff8294e6b5e";

        // when
        ResponseEntity<ApiResponse> response = restTemplate.getForEntity(url, ApiResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(Objects.requireNonNull(response.getBody()).getMessage()).isEqualTo("잘못된 날짜 형식입니다. yyyy-MM-dd 형식을 사용하세요.");
    }

    @Test
    @DisplayName("API 키 누락 시 403 Forbidden 반환 테스트")
    void testMissingApiKey() {
        // given - API key 미입력
        String url = "/api/v1/stocks?companyCode=AAPL&startDate=2019-12-01&endDate=2019-01-10";

        // when
        ResponseEntity<ApiResponse> response = restTemplate.getForEntity(url, ApiResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).contains("API key가 누락되었습니다.");
    }
}
