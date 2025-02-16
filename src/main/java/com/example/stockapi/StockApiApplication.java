package com.example.stockapi;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockApiApplication {
	public static void main(String[] args) {
		// 환경 변수 로드
		Dotenv dotenv = Dotenv.load();

		// 시스템 환경 변수로 설정
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(StockApiApplication.class, args);
	}
}
