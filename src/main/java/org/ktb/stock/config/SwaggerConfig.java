package org.ktb.stock.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info = @Info(
                title = "주식 정보 조회 API",
                description = "주식 정보를 조회하는 API 입니다.",
                version = "v1.0"
        )
)
@Configuration
public class SwaggerConfig {
}