package org.ktb.ktbbedevassignment.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.ktb.ktbbedevassignment.config.MapperConfig;
import org.ktb.ktbbedevassignment.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GlobalExceptionTestController.class)
@Import(MapperConfig.class)
class GlobalExceptionHandlerTest {

    // 변경 시 하드코딩된 GlobalExceptionTestController의 URL을 변경해야 함
    private static final String REQUEST_URL = "/test/exception";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private XmlMapper xmlMapper;

    @Nested
    @DisplayName("JSON 응답 테스트")
    class JsonResponseTest {

        @Test
        @DisplayName("필수 파라미터 누락 시 JSON 에러 응답을 반환")
        void missingParameter_ReturnsJsonError() throws Exception {
            ApiResponse<Object> expectedResponse =
                    ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");

            mockMvc.perform(get(REQUEST_URL))
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
        }
    }

    @Nested
    @DisplayName("XML 응답 테스트")
    class XmlResponseTest {

        @Test
        @DisplayName("필수 파라미터 누락 시 XML 에러 응답을 반환")
        void missingParameter_ReturnsXmlError() throws Exception {
            ApiResponse<Object> expectedResponse =
                    ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");

            mockMvc.perform(get(REQUEST_URL).param("format", "xml"))
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().xml(xmlMapper.writeValueAsString(expectedResponse)));
        }
    }
}

@RestController
@RequestMapping("/test")
class GlobalExceptionTestController {

    @GetMapping("/exception")
    public ResponseEntity<ApiResponse<List<String>>> testException() {

        throw new RuntimeException("Test exception");
    }
}
