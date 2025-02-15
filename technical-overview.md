# 기업 주가 조회 API 문서

## 프로젝트 개요
이 프로젝트는 특정 기업의 주가(종가) 정보를 조회하여 응답을 주는 RESTful API입니다. 사용자는 기업의 종가 데이터를 요청하고, API는 데이터베이스에서 해당 정보를 검색하여 응답합니다.

### 주요 기능
- 기업의 주가(종가) 조회 기능 제공
- RESTful API 설계 및 구현
- API Key 기반의 인증 및 보안 기능 적용
- JPA를 이용한 데이터 저장 및 조회

### API 요청 파라미터
| 파라미터       | 타입      | 필수 | 위치    | 설명 |
|--------------|---------|----|-------|--------------------------------|
| `companyCode` | `String` | O | Query  | 기업 코드 (예: `AAPL`) |
| `startDate`   | `String` | O | Query  | 조회 시작 날짜 (YYYY-MM-DD) |
| `endDate`     | `String` | O | Query  | 조회 종료 날짜 (YYYY-MM-DD) |
| `apikey`      | `String` | X | Query  | API 인증 키 (쿼리 파라미터로 전달 가능) |
| `format`      | `String` | X | Query  | 응답 형식 (기본값: `json`, 예: `xml`) |
| `x-api-key`   | `String` | X | Header | API 인증 키 (헤더로 전달 가능) |

### API 실행 결과 예시
<img src="https://github.com/user-attachments/assets/8f1f6afd-64dc-408e-b431-f23650a06744" width="500"><br>
API가 정상적으로 동작하면 위와 같은 JSON 응답을 받을 수 있습니다.

---

## 프로젝트 구조 설명

### 프로젝트 구조
```plaintext
src/main/java
│── com.ktb.assignment
│   ├── StockApplication.java (Spring Boot 메인 애플리케이션)
│   ├── stock.config
│   │   ├── ApiKeyConfig.java (API Key 설정)
│   ├── stock.controller
│   │   ├── StockController.java (REST 컨트롤러)
│   ├── stock.dto
│   │   ├── ResponseWrapper.java (응답 래퍼 클래스)
│   │   ├── StockHistoryResponse.java (주가 정보 응답 DTO)
│   │   ├── StockResponse.java (기업별 주가 응답 DTO)
│   ├── stock.entity
│   │   ├── Company.java (기업 정보 엔티티)
│   │   ├── StocksHistory.java (주가 정보 엔티티)
│   │   ├── StocksHistoryId.java (복합 키를 위한 ID 클래스)
│   ├── stock.exception
│   │   ├── StockException.java (예외 처리)
│   ├── stock.repository
│   │   ├── CompanyRepository.java (기업 정보 JPA 리포지토리)
│   │   ├── StocksHistoryRepository.java (주가 정보 JPA 리포지토리)
│   ├── stock.security
│   │   ├── ApiKeyValidator.java (API Key 검증 로직)
│   ├── stock.service
│       ├── StockService.java (비즈니스 로직 구현)
```

### 주요 클래스 설명
- StockController.java: HTTP 요청을 처리하는 컨트롤러
- StockService.java: 데이터 조회 및 비즈니스 로직 처리
- Company.java, StocksHistory.java: JPA 엔티티 클래스
- StocksHistoryId.java: 복합 키를 위한 ID 클래스로 사용
- CompanyRepository.java, StocksHistoryRepository.java: 데이터베이스 접근을 담당하는 JPA 리포지토리
- ApiKeyValidator.java: API Key를 검증

---

## 트러블슈팅
### 문제 상황
> `StocksHistory` 엔티티는 `date`와 `company_id`를 **복합 키**로 사용해야 하는데
> JPA에서 복합 키를 다루는 방식이 익숙하지 않아 문제가 발생했습니다.  
> 초기에 `@Id` 어노테이션을 두 개의 필드에 직접 적용하려 했으나
> 이는 JPA에서 지원되지 않는 방식이었습니다.

### 해결 방법
- 별도의 @Embeddable 클래스를 생성하여 복합 키를 정의 (StocksHistoryId.java)
- @EmbeddedId 어노테이션을 사용하여 엔티티에서 복합 키를 적용

### 해결 결과
복합 키를 정상적으로 인식하여 데이터 조회 및 삽입이 가능해졌습니다.

---

본 문서는 API의 주요 개념과 구현 방식 그리고 개발 과정에서 겪은 트러블슈팅을 정리한 내용입니다. 
앞으로 추가 개선 작업이 진행될 예정입니다.