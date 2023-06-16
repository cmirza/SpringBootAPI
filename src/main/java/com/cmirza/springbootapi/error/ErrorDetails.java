package com.cmirza.springbootapi.error;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timestamp, String message, HttpStatus httpStatus) { }
