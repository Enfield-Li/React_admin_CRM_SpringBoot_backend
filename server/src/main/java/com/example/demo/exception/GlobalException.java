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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalException extends RuntimeException {

  private static final Logger log = LoggerFactory.getLogger(
    GlobalException.class
  );

  // org.springframework.web.bind.MissingServletRequestParameterException
  @ExceptionHandler(Exception.class)
  protected ResponseEntity<String> catchAllException(Exception e) {
    log.error("\n \n **************** Uncaught Error ****************", e);

    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<String> catchParamArgsMismatch(Exception e) {
    log.error(e.getMessage());

    return ResponseEntity.badRequest().body("Invalid url parameter recieved.");
  }

  @ExceptionHandler(ItemNotFoundException.class)
  protected ItemNotFoundException catchItemNotFoundException(
    ItemNotFoundException e
  ) {
    return e;
  }

  @ExceptionHandler(value = { MethodArgumentNotValidException.class })
  public ResponseEntity<ValidationErrorResponse> catchInvalidRequestBodyInput(
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
}
