package com.edwin.edwin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class EdwinApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdwinApplication.class, args);
	}
}
