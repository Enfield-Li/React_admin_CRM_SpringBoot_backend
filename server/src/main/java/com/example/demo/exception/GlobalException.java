package com.example.demo.exception;

import com.example.demo.dto.ValidationErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
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

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<String> catchUncaughtException(Exception e) {
    log.error("\n \n **************** Uncaught Error ****************", e);

    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler(BadSqlGrammarException.class)
  protected ResponseEntity<String> catchBadSqlGrammarException(Exception e) {
    log.error(e.getMessage());

    return ResponseEntity
      .badRequest()
      .body("Invalid url parameter for sort/order/start/end options recieved.");
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<String> catchParamArgsMismatchException(
    Exception e
  ) {
    log.error(e.getMessage());

    return ResponseEntity.badRequest().body("Required url parameter missing.");
  }

  @ExceptionHandler(ItemNotFoundException.class)
  protected ItemNotFoundException catchItemNotFoundException(
    ItemNotFoundException e
  ) {
    return e;
  }

  @ExceptionHandler(value = { MethodArgumentNotValidException.class })
  public ResponseEntity<ValidationErrorResponse> catchInvalidRequestBodyInputException(
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
