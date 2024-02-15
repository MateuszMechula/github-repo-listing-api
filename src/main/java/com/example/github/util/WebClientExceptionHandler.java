package com.example.github.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Slf4j
@ControllerAdvice
public class WebClientExceptionHandler {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiError> handleWebClientException(WebClientResponseException ex) {
        log.error("WebClientResponseException occurred: {}", ex.getMessage());
        log.error("Status code: {}", ex.getStatusCode());
        ApiError apiError = ApiError.builder()
                .status(ex.getStatusCode().value())
                .message("Username doesn't exists")
                .build();
        return ResponseEntity.badRequest().body(apiError);
    }
}
