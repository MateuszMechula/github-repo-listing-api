package com.example.github.util;

import com.example.github.util.exception.ClientErrorException;
import com.example.github.util.exception.InternalServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class WebClientExceptionHandler {
    @ExceptionHandler(ClientErrorException.class)
    public ResponseEntity<HttpException> handleUsernameNotFound(ClientErrorException ex) {
        log.error("UsernameNotFoundException occurred: {}", ex.getMessage());
        HttpException httpException = new HttpException(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return new ResponseEntity<>(httpException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<HttpException> handleInternalServer(InternalServerException ex) {
        log.error("InternalServerException occurred: {}", ex.getMessage());
        HttpException httpException = new HttpException(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(httpException, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
