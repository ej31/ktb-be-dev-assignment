package org.ktb.stocks.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Component
@ConfigurationProperties("api.key")
public class ApiKeyProperty {
    private String query;
    private String header;
    private List<String> secrets = new ArrayList<>();
}
