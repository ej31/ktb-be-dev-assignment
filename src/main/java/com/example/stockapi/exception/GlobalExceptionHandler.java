package com.example.stockapi.exception;

import com.example.stockapi.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ErrorResponseDTO> createErrorResponse(String error, String message, HttpStatus status, HttpServletRequest request) {
        String format = request.getParameter("format");
        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);

        MediaType responseType = MediaType.APPLICATION_JSON;
        if ("xml".equalsIgnoreCase(format) || (acceptHeader != null && acceptHeader.contains("xml"))) {
            responseType = MediaType.APPLICATION_XML;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(responseType);

        return new ResponseEntity<>(new ErrorResponseDTO(error, message), headers, status);
    }

    // 비즈니스 예외 처리 (400, 403)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDTO> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        log.warn("Business Exception: {}", ex.getMessage());
        return createErrorResponse("Business Error", ex.getMessage(), ex.getStatus(), request);
    }

    // 시스템 예외 처리 (500)
    @ExceptionHandler(SystemException.class)
    public ResponseEntity<ErrorResponseDTO> handleSystemException(SystemException ex, HttpServletRequest request) {
        log.error("System Exception: {}", ex.getMessage(), ex);
        return createErrorResponse("System Error", ex.getMessage(), ex.getStatus(), request);
    }

    // 잘못된 요청 파라미터 (400 Bad Request) - 날짜 형식 오류 포함
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String message = "Invalid request parameter: " + ex.getName();

        if (ex.getRequiredType() != null && ex.getRequiredType().equals(java.time.LocalDate.class)) {
            message = "Invalid date format. Please use 'yyyy-MM-dd'.";
        }

        log.warn("Invalid Parameter: {}", message);
        return createErrorResponse("Bad Request", message, HttpStatus.BAD_REQUEST, request);
    }

    // 존재하지 않는 API 요청 (404 Not Found)
    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundException(HttpServletRequest request) {
        log.warn("API Not Found");
        return createErrorResponse("API Not Found", "API does not exist..", HttpStatus.NOT_FOUND, request);
    }

    // 기타 예외 처리 (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected Exception: {}", ex.getMessage(), ex);
        return createErrorResponse("Internal Server Error", "Unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
