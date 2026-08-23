package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<String> handleCustomException(ResourceNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  @ExceptionHandler(InvalidFieldException.class)
  public ResponseEntity<String> handleInvalidFieldException(InvalidFieldException e) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
  }

  @ExceptionHandler(DuplicateUsernameException.class)
  public ResponseEntity<String> handleDuplicateUsernameException(DuplicateUsernameException e){
    return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<String> handleUnauthorizedException(UnauthorizedException e){
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
  }
}