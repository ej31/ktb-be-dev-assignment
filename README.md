# pre_assignment
특정 기업의 주가(종가) 정보를 조회하여 응답을 주는 API 서버

## Tech Spec
- 언어: 자바
- 프레임워크: Springboot 3.x
- DB 라이브러리: Jdbc template

## 구현 상세
### 결과
- request

  http://localhost:8080/api/v1/stock?companyCode=AAPL&startDate=2024-01-02&endDate=2024-01-04
- response
  ```
  {
    "status": 200,
    "message": null,
    "data": [
        {
            "company_name": "Apple Inc.",
            "trade_date": "2024-01-02",
            "trade_price": 186
        },
        {
            "company_name": "Apple Inc.",
            "trade_date": "2024-01-03",
            "trade_price": 184
        },
        {
            "company_name": "Apple Inc.",
            "trade_date": "2024-01-04",
            "trade_price": 182
        }
    ]
  }

### API 설계 철학
1. **일관성**

   모든 API는 일관된 형식과 구조를 따라야 합니다. 이를 통해 클라이언트는 각기 다른 API의 응답을 쉽게 예측하고 처리할 수 있습니다. 모든 API응답은 다음과 같은 표준을 따릅니다.
```
  {
    "status": {상태 코드},
    "message": {"상세 설명 메시지"},
    "data": {결과값}
  }
```
2. **명확성**

   응답 메시지는 명확하고 구체적이어야 합니다. 오류 발생시 오류 원인을 설명하는 메시지를 제공합니다.
```
  if (apiKey == null) {
      throw new ApiKeyException(HttpServletResponse.SC_BAD_REQUEST, "No API key"); 
  }
```

### API Versioning
- URI Vesioning 방식
```
http://localhost:8080/api/v1/stock
```

### API Key 검증
- **헤더에 API Key 포함**
- 요청에 키가 없을 경우
  ```
  {
    "status": 400,
    "message": "No API key",
    "data": null
  }
- api 키 값이 틀릴 경우
  ```
  {
    "status": 403,
    "message": "Invalid API key",
    "data": null
  }

### Endpoint 확인
- 필수 파라미터(companyCode, startDate, endDate)가 없을 경우
  ```
  {
    "status": 400,
    "message": "No Parameter",
    "data": null
  }
- 존재하지 않는 api 호출
  ```
  {
    "status": 404,
    "message": "API endpoint not found",
    "data": null
  }

### 현재 진행 중인 작업
API Throttling 개발 중 ... :hammer: :hammer:

