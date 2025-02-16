#!/bin/bash
set -e

echo "🔥 [INFO] EntryPoint Script 시작됨"  # stdout에 로그 남기기

# app.jar 파일 존재 여부 확인
if [ -f "/app/app.jar" ]; then
  echo "✅ [INFO] app.jar 파일이 존재합니다."
else
  echo "❌ [ERROR] app.jar 파일을 찾을 수 없습니다! 애플리케이션을 실행할 수 없습니다." >&2
  exit 1
fi

# Spring Boot 애플리케이션 실행
echo "🚀 [INFO] 애플리케이션 실행 시작..."
exec java -jar /app/app.jar
