package org.ktb.ktbbedevassignment.aop;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.ktb.ktbbedevassignment.config.MapperConfig;
import org.ktb.ktbbedevassignment.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@WebMvcTest(controllers = JsonXmlMapperTestController.class)
@Import({JsonXmlResponseAspect.class, MapperConfig.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@EnableAspectJAutoProxy
class JsonXmlResponseAspectTest {

    // 변경 시 하드코딩된 JsonXmlMapperTestController의 URL을 변경해야 함
    private static final String REQUEST_URL = "/test/json-xml";

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("JSON 응답 테스트")
    class JsonResponseTest {

        @Test
        @DisplayName("Format이 존재하지 않은 경우 JSON 응답이 정상적으로 반환된다")
        void whenFormatIsNotSpecified_thenReturnsJsonResponse() throws Exception {
            performRequest("json", MediaType.APPLICATION_JSON);
        }

        @Test
        @DisplayName("Format이 json(소문자)인 경우 JSON 응답이 정상적으로 반환된다")
        void whenFormatIsLowercaseJson_thenReturnsJsonResponse() throws Exception {
            performRequest("json", MediaType.APPLICATION_JSON);
        }

        @Test
        @DisplayName("Format이 JSON(대문자)인 경우 JSON 응답이 정상적으로 반환된다")
        void whenFormatIsUppercaseJson_thenReturnsJsonResponse() throws Exception {
            performRequest("JSON", MediaType.APPLICATION_JSON);
        }

        @Test
        @DisplayName("Format이 JsOn(대, 소문자 혼합)인 경우 JSON 응답이 정상적으로 반환된다")
        void whenFormatIsMixedCaseJson_thenReturnsJsonResponse() throws Exception {
            performRequest("JsOn", MediaType.APPLICATION_JSON);
        }
    }

    @Nested
    @DisplayName("XML 응답 테스트")
    class XmlResponseTest {

        @Test
        @DisplayName("Format이 xml(소문자)인 경우 XML 응답이 정상적으로 반환된다")
        void whenFormatIsLowercaseXml_thenReturnsXmlResponse() throws Exception {
            performRequest("xml", MediaType.APPLICATION_XML);
        }

        @Test
        @DisplayName("Format이 XML(대문자)인 경우 XML 응답이 정상적으로 반환된다")
        void whenFormatIsUppercaseXml_thenReturnsXmlResponse() throws Exception {
            performRequest("XML", MediaType.APPLICATION_XML);
        }

        @Test
        @DisplayName("Format이 xMl(대, 소문자 혼합)인 경우 XML 응답이 정상적으로 반환된다")
        void whenFormatIsMixedCaseXml_thenReturnsXmlResponse() throws Exception {
            performRequest("xMl", MediaType.APPLICATION_XML);
        }
    }

    private void performRequest(String format, MediaType expectedMediaType) throws Exception {
        if (format.equalsIgnoreCase("json")) {
            mockMvc.perform(get(REQUEST_URL)
                        .param("format", format))
                .andExpect(status().isOk())
                .andExpect(content().contentType(expectedMediaType))
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("Success"));
            return;
        }

        if (format.equalsIgnoreCase("xml")) {
            mockMvc.perform(get(REQUEST_URL)
                        .param("format", format))
                .andExpect(status().isOk())
                .andExpect(content().contentType(expectedMediaType))
                .andExpect(xpath("/ApiResponse/status").string("200"))
                .andExpect(xpath("/ApiResponse/message").string("Success"));
        }
    }
}

@RestController
@RequestMapping("/test")
class JsonXmlMapperTestController {

    @GetMapping("/json-xml")
    @JsonXmlResponse
    public ResponseEntity<ApiResponse<List<String>>> testJsonXml() {
        List<String> testData = List.of("Data1", "Data2", "Data3");
        ApiResponse<List<String>> result = ApiResponse.success(testData);
        return ResponseEntity.ok(result);
    }
}
