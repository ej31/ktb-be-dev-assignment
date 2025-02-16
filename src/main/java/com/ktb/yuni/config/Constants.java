package com.ktb.yuni.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
@Configuration
@ConfigurationProperties(prefix = "api")
@Getter
@Setter
public class Constants {
    @NotBlank(message = "API key가 필요합니다.")
    private String key;
}
