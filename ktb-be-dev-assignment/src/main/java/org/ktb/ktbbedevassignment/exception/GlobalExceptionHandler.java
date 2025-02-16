package org.ktb.ktbbedevassignment.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.ktb.ktbbedevassignment.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper;

    public GlobalExceptionHandler(
            @Qualifier("objectMapper") ObjectMapper objectMapper, // MapperConfig 의 objectMapper 빈을 주입
            XmlMapper xmlMapper
    ) {
        this.objectMapper = objectMapper;
        this.xmlMapper = xmlMapper;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpServletRequest request) throws Exception {

        logger.warn("필수 요청 파라미터 누락: {}", ex.getParameterName(), ex);

        ApiResponse<Object> response = ApiResponse.error(HttpStatus.BAD_REQUEST, "필수 요청 파라미터가 없습니다: " + ex.getParameterName());
        return getFormattedResponse(request, response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<String> handleCompanyNotFoundException(
            CompanyNotFoundException ex, HttpServletRequest request) throws Exception {

        logger.warn("존재하지 않는 회사 요청: {}", ex.getReason(), ex);

        ApiResponse<Object> response = ApiResponse.error(HttpStatus.NOT_FOUND, ex.getReason());
        return getFormattedResponse(request, response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleNoResourceFoundException(
            NoResourceFoundException ex, HttpServletRequest request) throws Exception {

        logger.warn("리소스를 찾을 수 없음: {}", ex.getMessage(), ex);

        ApiResponse<Object> response = ApiResponse.error(HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없습니다.");
        return getFormattedResponse(request, response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request) throws Exception {

        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();

        logger.warn("유효성 검증 실패: {}", errorMessage, ex);

        ApiResponse<Object> response = ApiResponse.error(HttpStatus.BAD_REQUEST, errorMessage);
        return getFormattedResponse(request, response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(
            Exception ex, HttpServletRequest request) throws Exception {

        logger.error("서버 내부 오류 발생: {}", ex.getMessage(), ex);

        ApiResponse<Object> response = ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");
        return getFormattedResponse(request, response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> getFormattedResponse(HttpServletRequest request, ApiResponse<Object> response, HttpStatus status) throws Exception {
        String format = request.getParameter("format") != null ? request.getParameter("format") : "json";

        if (format.equals("xml")) {
            return ResponseEntity.status(status)
                    .header("Content-Type", "application/xml; charset=UTF-8")
                    .body(xmlMapper.writeValueAsString(response));
        }

        return ResponseEntity.status(status)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(objectMapper.writeValueAsString(response));
    }
}
