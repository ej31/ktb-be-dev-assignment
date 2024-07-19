# 주디 컴퍼니 과제

이력서 작성을 핑계로 사전 과제를 늦게 제출해서 죄송합니다 ㅠㅠ제프 정말 감사합니다
아침이 열어주셔서 제프 정말 감사드립니다.

## 개요

이 프로젝트는 다양한 기업의 주가(종가) 정보를 조회하고 표시하는 웹 애플리케이션입니다.
<br>
프론트엔드는 React와 Material-UI를 사용하여 구축되었으며,
<br>
백엔드는 Spring Boot를 사용하여 MySQL 데이터베이스와 연동됩니다.

## 실행 화면

### [- 주가정보조회 실행 영상 링크 (클릭)](https://www.notion.so/536175661cf044288070ed374a0a3fb8?pvs=4)
### Company 조회
![img_1.png](images/img_1.png)
### 주가 정보 조회
![img.png](images/img.png)

## 목차

- [사용된 기술](#사용된-기술)
- [설치 방법](#설치-방법)
- [백엔드 구조](#백엔드-구조)
- [프론트엔드 구조](#프론트엔드-구조)
- [API 엔드포인트](#api-엔드포인트)
- [API 설계 철학](#api-설계-철학)
- [트러블슈팅](#트러블슈팅)
- [테스트](#테스트)

## 사용된 기술

- **프론트엔드** : React, Material-UI, Axios
- **백엔드** : Spring Boot, Hibernate, MySQL
- **데이터베이스** : MySQL - AWS 외부 DB 연결

## 설치 방법

### 백엔드

1. **리포지토리 클론**
    ```bash
    git clone <repository-url>
    cd backend
    ```

2. **`application.properties` 업데이트**
<br>
    `src/main/resources/application.properties` 파일을 열고 데이터베이스 자격 증명으로 업데이트합니다.
    ```properties
    spring.datasource.url=jdbc:mysql://<your-database-url>:3306/assignment
    spring.datasource.username=<your-username>
    spring.datasource.password=<your-password>
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    spring.jpa.properties.hibernate.format_sql=true
    spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
    spring.mvc.cors.allowed-origins=http://localhost:3000
    spring.mvc.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
    spring.mvc.cors.allowed-headers=*
    spring.mvc.cors.allow-credentials=true
    ```

3. **백엔드 빌드 및 실행**
    ```bash
    ./gradlew bootRun
    ```

### 프론트엔드

1. **프론트엔드 디렉토리로 이동**
    ```bash
    cd ../frontend
    ```

2. **의존성 설치**
    ```bash
    npm install
    ```

3. **프론트엔드 시작**:
    ```bash
    npm start
    ```

## 백엔드 구조

```plaintext
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── judycompany/
│   │   │           └── assignment1/
│   │   │               ├── Application.java
│   │   │               ├── config/
│   │   │               │   └── CorsConfig.java
│   │   │               ├── v1/
│   │   │               │   ├── controller/
│   │   │               │   │   └── StockController.java
│   │   │               │   ├── model/
│   │   │               │   │   ├── Company.java
│   │   │               │   │   └── StockHistory.java
│   │   │               │   ├── repository/
│   │   │               │   │   ├── CompanyRepository.java
│   │   │               │   │   └── StockRepository.java
│   │   │               │   └── service/
│   │   │               │       ├── StockService.java
│   │   │               │       ├── QuotaService.java
│   │   │               │       └── QuotaServiceImpl.java
│   ├── resources/
│   │   └── application.properties
└── build.gradle
```

## 프론트엔드 구조

```plaintext
frontend/
├── public/
│   └── index.html
├── src/
│   ├── components/
│   │   └── StockHistory.js
│   ├── App.js
│   ├── index.js
│   └── reportWebVitals.js
├── package.json
```

## API 엔드포인트

### GET /api/v1/companies

- **설명**: 모든 회사 목록을 조회합니다.
- **쿼리 파라미터**
  - `apikey` (필수): API 키
- **응답 예시**
  ```json
  [
    {
      "companyCode": "AAPL",
      "companyName": "Apple Inc."
    },
    {
      "companyCode": "GOOGL",
      "companyName": "Alphabet Inc."
    }
  ]
  ```

### GET /api/v1/stocks/history

- **설명**: 특정 회사의 주가(종가) 정보를 조회합니다.
- **쿼리 파라미터**
  - `companyCode` (필수): 회사 코드
  - `startDate` (필수): 조회 시작 날짜 (yyyy-mm-dd)
  - `endDate` (필수): 조회 종료 날짜 (yyyy-mm-dd)
  - `apikey` (필수): API 키
- **응답 예시**
  ```json
  [
    {
      "companyName": "Apple Inc.",
      "tradeDate": "2024-01-02",
      "closePrice": 150
    },
    {
      "companyName": "Apple Inc.",
      "tradeDate": "2024-01-03",
      "closePrice": 152
    }
  ]
  ```

## API 설계 철학

### 버저닝

배포된 OPEN API 서비스를 변경하는 것은 쉽지 않은 작업이므로
<br>확장 가능하고 일관성 있는 API 및 파라미터 설계에 처음부터 많은 노력을 기울였습니다.
<br>또한 변경이 불가피하게 필요할 경우를 대비하여 API 버저닝을 고려하여 설계했습니다.
- 현재 버전은 `/api/v1`로 명시되어 있고, 패키지를 분리하도록 설계했습니다.

### 응답 형식

API의 응답은 일관성 있고 확장 가능하도록 설계되었습니다.
<br>
성공 및 실패에 대한 일관성 있는 응답 포맷을 제공합니다.

### API Throttling

API 서버의 안정성을 위해 특정 API 키에 대한 호출을 10초에 10건으로 제한하는 Quota 기능을 구현했습니다.
<br>API 서버가 과도한 요청으로 인해 불안정해지는 것을 방지합니다.

## 트러블슈팅

### 데이터베이스 연결 문제

데이터베이스 연결 오류가 발생할 경우, `application.properties` 파일에서 데이터베이스 자격 증명이 올바른지 확인하십시오.

### API 호출 제한 초과

API 호출 제한을 초과한 경우 `429 Too Many Requests` 응답을 받게 됩니다. 이 경우 일정 시간 후 다시 시도하십시오.

## 테스트

테스트 코드는 API의 특성상 사용자의 관점에서 접근하여 스펙(설계)을 기반으로 테스트를 수행합니다.
<br>기능뿐만 아니라 스펙에 대한 검증도 동시에 진행합니다.

### 단위 테스트

단위 테스트는 각 개별 컴포넌트의 기능을 검증합니다.

### 통합 테스트

통합 테스트는 여러 컴포넌트가 함께 동작하는 방식을 검증합니다.

## 끝

이 프로젝트에 대한 피드백이나 질문이 있다면 언제든지 연락해 주세요. 감사합니다!
<br>
주디 최고 제프 짱!