package com.example.stockapi;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StockApiApplication {

	private static Dotenv dotenv;

	@Autowired
	public StockApiApplication(Dotenv dotenv) {
		StockApiApplication.dotenv = dotenv;
	}

	public static void main(String[] args) {
		SpringApplication.run(StockApiApplication.class, args);
	}
}
