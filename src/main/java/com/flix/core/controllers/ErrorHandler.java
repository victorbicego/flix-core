package com.flix.core.controllers;

import com.flix.core.exceptions.InvalidOperationException;
import com.flix.core.exceptions.NotFoundException;
import jakarta.validation.ValidationException;
import java.net.URI;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex,
      @Nullable Object body,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    if (body == null
        || (body instanceof ProblemDetail detail
            && detail.getType().equals(URI.create("about:blank")))) {
      var message = ex.getMessage() != null ? ex.getMessage() : "";
      body = Map.of("type", ex.getClass(), "message", message);
    }
    return super.handleExceptionInternal(ex, body, headers, status, request);
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
    HttpHeaders headers = new HttpHeaders();
    return handleExceptionInternal(ex, null, headers, HttpStatus.NOT_FOUND, request);
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleOperationInvalidException(
      InvalidOperationException ex, WebRequest request) {
    HttpHeaders headers = new HttpHeaders();
    return handleExceptionInternal(ex, null, headers, HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleValidationException(
      ValidationException ex, WebRequest request) {
    HttpHeaders headers = new HttpHeaders();
    return handleExceptionInternal(ex, null, headers, HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
    HttpHeaders headers = new HttpHeaders();
    return handleExceptionInternal(ex, null, headers, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
