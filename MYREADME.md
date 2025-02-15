## 구현내용

### API 디자인
- 버저닝 적용(현재 v1)

---

### API 응답값
  - createErrorResponse() 메서드를 사용하여 모든 에러 응답을 통일
  - GlobalExceptionHandler에서 예외를 전역적으로 처리하여 일관성 유지

    #### 성공 응답
    | 상태 코드  | 설명                      |
    |------------|--------------------------|
    | `200 OK`   | 주식 정보 정상 응답       |

    #### 오류 응답
    | 상태 코드        | 설명                                     |
    |-----------------|----------------------------------------|
    | `400 Bad Request` | 필수 파라미터(`companyCode`, `startDate`, `endDate`)가 누락된 경우 |
    | `400 Bad Request` | API Key가 요청에 포함되지 않은 경우 |
    | `403 Forbidden` | 제공된 API Key가 유효하지 않은 경우 |
    | `404 Not Found` | 존재하지 않는 API 엔드포인트 요청 시 |

---

### 유효성 검사
1) API 유효성 검사
   - 헤더(x-api-key) 또는 쿼리 파라미터(apikey) 로 제공되지 않으면 400 Bad Request
   - API Key 값이 올바르지 않으면 403 Forbidden
2) 필수 파라미터 검증
    - 누락된 필수 파라미터가 있다면 각각 다른 메시지로 400 Bad Request 반환
3) 날짜 형식 검증
    - startDate와 endDate는 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)으로 yyyy-MM-dd 으로 변환되도록 처리
    - 잘못된 날짜 형식이면 자동으로 400 Bad Request
4) 존재하지않는 API 요청
    - Spring Boot의 GlobalExceptionHandler에서 처리

---

