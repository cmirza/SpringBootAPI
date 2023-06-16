package com.cmirza.springbootapi.controller;

import com.cmirza.springbootapi.error.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@ControllerAdvice
public class UserControllerExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("IllegalArgumentException occurred: ", ex);

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
        logger.error("NullPointerException occurred: ", ex);

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Object> handleException(Exception ex) {
        logger.error("Exception occurred: ", ex);

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
