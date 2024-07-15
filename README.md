# Dev Assignment

특정 기업의 주가 정보 조회하여 응답을 주는 API 서버 개발
## Features
### 기본 요구 사항
- [ ] 특정 기업의 주가 정보 조회가 잘 작동하는가?
- [ ] 요청에 키를 포함하는가?
- [ ] 요청 포맷을 맞추었는가?
  - company_code, stocks_history, trade_date
### 추가 요구 사항
- [ ] API 디자인을 하였는가?
  - [ ] API 버저닝을 구현하였는가?
  - [ ] API 에 대한 문서화가 잘 되었는가?
- [ ] 일관된 응답 포맷을 설계하였는가?
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

- [ ] Request 요구 사항 만족
- [ ] Response 요구 사항 만족
- [ ] Status Code 요구 사항 만족

### 추가

- [ ] 다양한 형태의 응답 형식 구현 (json, xml 등)
- [ ] 일관성 있는 공통 응답 포맷
- [ ] API 버저닝
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