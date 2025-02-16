````# 📈 기업 주식 정보 조회 API 문서

기업의 주식 종가를 조회할 수 있는 API입니다.

## 🔑 인증 정보 (API 키)
- **모든 API 요청 시, 유효한 API 키를 포함해야 합니다.**
- **API Key**: `c18aa07f-f005-4c2f-b6db-dff8294e6b5e`
- **API Key 전달 방식**:
    - `Header`: `x-api-key: {API_KEY}`
    - `Query Param`: `?apiKey={API_KEY}`

## 🚀 엔드포인트 명세
### 주가 조회 API
- **HTTP Method**: `GET`
- **URL**: `/api/v1/stocks`

### 요청 파라미터
| 파라미터명    | 타입    | 필수여부 | 설명                        |
|-----------|-------|-----|---------------------------|
| `companyCode` | `string` | yes | 기업의 종목 코드                  |
| `startDate`   | `string` | yes  | 조회 시작 날짜 (yyyy-MM-dd)    |
| `endDate`     | `string` | yes️ | 조회 종료 날짜 (yyyy-MM-dd)    |
| `apiKey`      | `string` | yes️ | API 키                      |

#### 요청 예제
- **Query Parameter 방식**
    ```http
    GET /api/v1/stocks?companyCode=YUNI&startDate=2020-01-01&endDate=2020-01-03&apiKey=c18aa07f-f005-4c2f-b6db-dff8294e6b5e
    ```

- **Header 방식**
    ```http
    GET /api/v1/stocks?companyCode=YUNI&startDate=2020-01-03&endDate=2020-01-03 HTTP/1.1
    Host: localhost:8080
    x-api-key: c18aa07f-f005-4c2f-b6db-dff8294e6b5e
    ```

### 응답 예제 (성공)

```json
{
  "success": true,
  "message": "주식 데이터 조회 성공",
  "data": {
    "companyName": "Apple Inc.",
    "stocks": [
      {
        "tradeDate": "2020-01-02",
        "closingPrice": 72.7161
      }
    ]
  },
  "status": 200
}
```

### 응답 필드 설명
| **필드명**    | **타입**    | **설명**                           |
|------------|--------|------------------------------|
| `success` | `boolean` | 요청 성공 여부 (`true`/`false`)     |
| `message` | `string`  | 요청 처리 결과 메시지              |
| `data`    | `object`  | 조회된 주식 데이터 객체             |
| └─ `companyName` | `string` | 회사 이름                     |
| └─ `stocks`      | `array`  | 주식 데이터 배열                |
|    └─ `tradeDate`   | `string` | 거래일 (yyyy-MM-dd)           |
|    └─ `closingPrice`| `float`  | 종가                          |
| `status` | `integer` | HTTP 상태 코드                  |


## ⚠️ 에러 응답 정의

| HTTP 코드 | 에러 메시지                                    | 설명                                  |
|-----------|--------------------------------------|-------------------------------------|
| `400`    | `필수 파라미터 '{parameter}' 누락`          | 필수 파라미터가 누락된 경우               |
| `400`    | `잘못된 날짜 형식입니다. yyyy-MM-dd 형식을 사용하세요.` | 날짜 포맷이 올바르지 않은 경우             |
| `403`    | `API key가 누락되었습니다.`                | API 키가 요청에 포함되지 않은 경우          |
| `403`    | `올바르지 않은 API key 입니다.`              | API 키가 올바르지 않은 경우               |
| `404`    | `회사 코드 '{companyCode}'를 찾을 수 없습니다.` | 존재하지 않는 회사 코드를 요청한 경우       |
````