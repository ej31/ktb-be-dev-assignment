package com.edwin.edwin.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("백엔드 사전과제 API 문서")
                .version("v1")
                .description("API 명세서");

        Server devServer = new Server()
                .url("http://localhost:8080")
                .description("개발용 서버");

        return new OpenAPI()
                .info(info)
                .addServersItem(devServer);
    }
}
