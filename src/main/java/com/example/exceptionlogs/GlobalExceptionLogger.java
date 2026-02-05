package com.example.exceptionlogs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionLogger {
  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionLogger.class);

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception ex) {
    // Log with stacktrace to feed the analyzer agent.
    log.error("Unhandled exception occurred", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErrorResponse("ERROR", ex.getClass().getSimpleName(), ex.getMessage()));
  }

  public record ErrorResponse(String status, String exception, String message) {}
}
