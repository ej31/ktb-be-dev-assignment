package ktb.mason.be_assignment.global.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@AllArgsConstructor
@ConfigurationProperties(prefix = "api")
@ConfigurationPropertiesBinding
public class ApiProperties {
	private final String key;
}