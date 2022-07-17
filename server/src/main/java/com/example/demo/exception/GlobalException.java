package com.example.demo.exception;

import com.example.demo.dto.ValidationErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException extends RuntimeException {

  private static final Logger log = LoggerFactory.getLogger(
    GlobalException.class
  );

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<String> catchAllException(Exception e) {
    log.error("\n \n **************** Uncaught Error ****************", e);

    return ResponseEntity
      .status(HttpStatus.INTERNAL_SERVER_ERROR)
      .body("Something's gone wrong...");
  }

  @ExceptionHandler(value = { MethodArgumentNotValidException.class })
  public ResponseEntity<ValidationErrorResponse> invalidInput(
    MethodArgumentNotValidException exception
  ) {
    FieldError fieldError = exception.getFieldError();

    return ResponseEntity
      .badRequest()
      .body(
        new ValidationErrorResponse(
          fieldError.getField(),
          fieldError.getDefaultMessage()
        )
      );
  }

  @ExceptionHandler(ItemNotFoundException.class)
  protected ItemNotFoundException catchItemNotFoundException(
    ItemNotFoundException e
  ) {
    return e;
  }
}
