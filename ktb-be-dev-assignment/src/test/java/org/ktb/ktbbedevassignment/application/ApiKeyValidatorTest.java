package org.ktb.ktbbedevassignment.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.ktb.ktbbedevassignment.exception.InvalidApiKeyException;
import org.ktb.ktbbedevassignment.exception.NotMatchedApiKeyException;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApiKeyValidatorTest {

    private final String validApiKey = "testValidApiKey";

    @Nested
    @DisplayName("validateApiKey 테스트")
    class validateApiKeyTest {

        @Nested
        @DisplayName("성공 케이스")
        class SuccessCases {

            @Test
            @DisplayName("API 키가 유효한 경우 반환값 없이 성공한다.")
            void validateApiKey_WhenApiKeyIsValid_ReturnsNothing() {
                // given
                String apiKey = validApiKey;
                ApiKeyValidator apiKeyValidator = new ApiKeyValidator(validApiKey);

                // when
                apiKeyValidator.validateApiKey(apiKey);

                // then
                // 예외가 발생하지 않으면 성공
            }
        }

        @Nested
        @DisplayName("실패 케이스")
        class FailureCases {

            @Test
            @DisplayName("API 키가 null인 경우 InvalidApiKeyException 예외를 던진다.")
            void validateApiKey_WhenApiKeyIsNull_ThrowsInvalidApiKeyException() {
                // given
                String apiKey = null;
                ApiKeyValidator apiKeyValidator = new ApiKeyValidator(validApiKey);

                // when & then
                assertThrows(InvalidApiKeyException.class, () -> apiKeyValidator.validateApiKey(apiKey));
            }

            @Test
            @DisplayName("입력한 API 키가 맞지 않을 경우 NotMatchedApiKeyException 예외를 던진다.")
            void validateApiKey_WhenApiKeyIsInvalid_ThrowsNotMatchedApiKeyException() {
                // given
                String apiKey = "invalidApiKey";
                ApiKeyValidator apiKeyValidator = new ApiKeyValidator(validApiKey);

                // when & then
                assertThrows(NotMatchedApiKeyException.class, () -> apiKeyValidator.validateApiKey(apiKey));
            }
        }
    }
}
