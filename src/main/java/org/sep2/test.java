package org.sep2;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class test {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Value("${api.key}")
    private String apiKey;

    @PostConstruct
    public void checkEnv() {
        System.out.println(" DB URL: " + dbUrl);
        System.out.println(" DB User: " + dbUser);
        System.out.println(" DB Password: " + dbPassword);
        System.out.println(" API Key: " + apiKey);
    }
}