package com.example.demo.config.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException extends RuntimeException {

  private static final Logger log = LoggerFactory.getLogger(
    GlobalException.class
  );

  @ExceptionHandler(value = { Exception.class })
  protected ResponseEntity<String> catchAllException(Exception e) {
    log.error("\n \n **************** Uncaught Error ****************", e);

    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body("Something's gone wrong...");
  }
}
