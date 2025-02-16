FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY build/libs/app.jar app.jar

RUN chmod +x app.jar

# 5️⃣ 환경 변수 파일을 지원하기 위해 envsubst 설치
RUN apk add --no-cache bash gettext

COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/entrypoint.sh"]
