package ktb.mason.be_assignment.global.validate;

import jakarta.servlet.http.HttpServletRequest;
import ktb.mason.be_assignment.global.api.ApiException;
import ktb.mason.be_assignment.global.api.AppHttpStatus;
import ktb.mason.be_assignment.global.properties.ApiProperties;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Aspect
@Component
@RequiredArgsConstructor
public class ApiKeyAspect {

    private final HttpServletRequest request;
    private final ApiProperties apiProperties;

    @Before("@annotation(ApiKey)")
    public void validateApiKey() {
        String apiKey = request.getHeader("x-api-key");

        if (!StringUtils.hasText(apiKey)) {
            throw new ApiException(AppHttpStatus.BAD_REQUEST);
        }

        if (!apiProperties.getKey().equals(apiKey)) {
            throw new ApiException(AppHttpStatus.FORBIDDEN);
        }
    }
}