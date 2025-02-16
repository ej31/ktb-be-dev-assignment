FROM openjdk:17-jdk-slim

# JAR 파일 복사
COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

EXPOSE 8080
