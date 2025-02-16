package com.ktb.yuni;

import com.ktb.yuni.config.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(Constants.class)
public class YuniApplication {

	public static void main(String[] args) {
		SpringApplication.run(YuniApplication.class, args);
	}

}
