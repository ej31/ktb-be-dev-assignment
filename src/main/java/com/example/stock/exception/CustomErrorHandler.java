package com.example.stock.exception;

import org.apache.coyote.Response;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CustomErrorHandler implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleNotFound() {
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Not Found");
        response.put("message", "The requested API does not exist.");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);    // 404
    }
}