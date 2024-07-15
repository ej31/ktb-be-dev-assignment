# Dev Assignment

특정 기업의 주가 정보 조회하여 응답을 주는 API 서버 개발

# API 디자인

## Versioning

- 버전 별로 엔드포인트가 다릅니다.
- 버전 1의 경우`/v1/`, 버전 2의 경우`/v2/`를 붙입니다.
    - 예시 : `/v1/companies/NVDA/price/closing`, `/v2/path/to/api`
- 버전을 변경하는 경우는 Breaking change 가 있을 때 변경합니다.
    - 요청 형식이 달라지는 경우
    - 응답 형식이 달라지는 경우
    - 타입이 변경되는 경우
    - 경로가 달라지는 경우

## Branches

버저닝에 맞는 개발 브랜치를 사용하였습니다.    
`main` 브랜치와 `develop` 브랜치 외에 다른 의미있는 브랜치는 없습니다.

### main

`main` 브랜치는 항상 최신 릴리즈입니다.    
제일 높은 버전의 릴리즈를 가리킵니다.

### main-v{number}

`main-vN` 브랜치는 N번째 버전의 최신 릴리즈입니다.

### develop-v{number}

`develop-v{number}` 브랜치는 {number}번째 버전을 개발하는 개발 브랜치입니다.   
예시 : `develop-v1`

### feat-{name}

기능 개발 브랜치입니다. 기능 개발이 완료된 경우 develop으로 병합하게 됩니다.

## Endpoint

형식 : `/v1/companies/{companyCode}/price/closing`

- 기본적으로 `/v1/companies/회사-코드/상위-카테고리/하위-카테고리`을 염두에 두고 설계하였습니다.
- 기업 코드가 많지 않고, api와 겹칠 가능성이 거의 없다고 판단하여 `PathVariable` 로 API를 구성하였습니다.
    - 겹친다고 하여도, route 순서상 문제 생길 확률이 낮다고 판단하였습니다.
- 만약 시작가, 평균가 API를 구현한다면,
  `/v1/companies/{companyCode}/prices/open`,`/v1/companies/{companyCode}/price/avg` 로 일관성 있는 설계가 가능합니다.

## 변수명

API 서비스를 구현한다고 하였을 때, 제일 중요한 것은 요청,응답 변수명을 통일하는 것이라고 생각합니다.       
기본적으로 카멜케이스로 작성하며, 아래의 내용을 따릅니다.

| 단어         | 변수명          |   
|------------|--------------|   
| **검색 시작일** | startDate    |   
| **검색 종료일** | endDate      |   
| **거래일**    | tradeDate    |
| **회사 코드**  | companyCode  |
| **회사명**    | companyName  |   
| **시가**     | openingPrice |   
| **종가**     | closingPrice |   
| **평균가**    | avgPrice     |   
| **거래량**    | volume       |   
| **최고가**    | highPrice    |   
| **최저가**    | lowPrice     |

## 응답 포맷

기본적으로 HTTP Status 코드로 상태를 표시하고, 추가적인 내용이 있을 경우 `message`로 전달합니다.
> [!INFO]   
> HTTP 상태 코드만으로도 현 API 구현상 충분히 상태를 설명 할 수 있다고 생각합니다.    
> 그 외의 상황을 세부적으로 설명하는 것은 사용자에게 도움이 되지만, 그것을 코드화 할 필요성은 현 상황상 없는 것 같습니다.

- 200 : 정상 작동 경우
- 400 : 파라미터가 잘못된 경우
- 403 : 인증 정보가 잘못되어 인증할 수 없는 경우
- 404 : 존재하지 않는 API를 호출한 경우
- 429 : API 시간당 허용량을 넘길 경우
- 500 : 서버에 에러가 발생해 정상적인 응답을 할 수 없는 경우

### 200 응답 예시 (v1)

```json5
{
  "message": "OK",
  "data": {
    // 내용
  }
}
```

### 40X 응답 예시 (v1)

```json5
{
  "message": "에러 상황 설명",
  "data": {
    // 에러 상황을 설명할 수 있는 데이터
    // 혹은 에러가 발생한 데이터
  }
}
```

---

## Features

### 기본 요구 사항

- [x] 특정 기업의 주가 정보 조회가 잘 작동하는가?
- [x] 요청에 키를 포함하는가?
- [x] 요청 포맷을 맞추었는가?
    - companyCode, startDate, endDate

### 추가 요구 사항

- [x] API 디자인을 하였는가?
    - [x] API 버저닝을 구현하였는가?
    - [x] API 에 대한 문서화가 잘 되었는가?
- [x] 일관된 응답 포맷을 설계하였는가?
- [ ] 테스트를 작성하였는가?
    - [ ] 최소 기능 수준까지 검증 할 수 있는가?
    - [ ] 스트레스 테스트를 진행 하였는가?
- [ ] API Throttling 을 구현하였는가?
    - 10초에 10건
- [ ] 다양한 응답 형식을 제공하는가?
    - json, xml 등

----

## Stack

- Spring WebFlux
- Spring R2DBC
    - io.asyncer:r2dbc-mysql : R2DBC mysql 구현체
- Lombok
- Spring devtools

## 구현 사항

### 기본

- [x] Request 요구 사항 만족
- [x] Response 요구 사항 만족
- [x] Status Code 요구 사항 만족

### 추가

- [ ] 다양한 형태의 응답 형식 구현 (json, xml 등)
- [x] 일관성 있는 공통 응답 포맷
- [x] API 버저닝
- [ ] API Throttling
- [ ] Test Code

## 데이터베이스

2개의 테이블 존재

- company : 기업 정보를 저장한 테이블
    - company_code : **PK**, varchar(100)
    - company_name : varchar(100)
- stocks_history
    - id : **PK**, **AI**, int
    - company_code : **FK**, varchar(100)
    - trade_date : date
        - 거래일
    - open_price : bigint
        - 시작가
    - high_price : bigint
        - 최고가
    - low_price : bigint
        - 최저가
    - close_price : bigint
        - 종가
    - volume : bigint
        - 거래량

## Request

- Header
    - x-api-key : `c18aa07f-f005-4c2f-b6db-dff8294e6b5e`
- Body
    - company_code : 회사명
    - start_date : 시작 날짜
    - end_date : 종료 날짜

## Response

요청 회사의 시작 날짜 ~ 종료 날짜의 모든 종가

- companyName: 회사명
- tradeDate : 거래일, 포맷 yyyy-mm-dd
- closingPrice : 종가

**포함 내용**     
요청 회사의 시작 날짜 ~ 종료 날짜의 모든 종가

> - companyName: 회사명
> - tradeDate : 거래일, 포맷 yyyy-mm-dd
> - closingPrice : 종가

## status codes

- 200 : 정상 상황
- 400
    - 요청에 키가 없는 경우
    - 필수 파라미터가 오지 않은 경우
- 403 : 키가 틀린 경우
- 404 : 존재하지 않는 API를 호출할 경우
- 500 : 서버 에러