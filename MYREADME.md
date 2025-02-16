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

### Custom Exception, log rotation
application.properties(기본지원)과 logback-spring.xml 중 application.properties 선택<br>
이유 : 간단하게 설정 가능하며 다른 XML 설정이 불필요하였기 때문.<br>
but 세부적인 로그 설정이 필요하면 logback-spring.xml을 사용할 예정

📌 **설정 내용**
- **로그 파일 최대 크기**: 10MB 이상이면 자동으로 새로운 파일 생성
- **로그 보관 기간**: 30일간 로그 유지 (이후 자동 삭제)
- **전체 로그 저장 용량**: 1GB를 초과하면 오래된 로그부터 삭제
- **로그 파일 저장 경로**: `logs/app.log`

---

### 응답형식
사용자가 원하는 응답 포맷을 선택할 수 있도록 ContentNegotiationManager를 활용하여 자동 변환 지원<br>

- /api/v1/stocks.json → JSON 응답
- /api/v1/stocks.xml → XML 응답
- /api/v1/stocks?format=json → JSON 응답
- /api/v1/stocks?format=xml → XML 응답
- 별도 지정이 없으면 기본 JSON 응답