package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.rest.config;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.exception.AntragstellerNotFoundException;
import io.github.domainprimitives.validation.InvariantException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class AntragstellerRestAdvicer extends ResponseEntityExceptionHandler {

  record ErrorResource(String name, String errorMessage) {}

  private ErrorResource buildResource(RuntimeException exception) {
    return new ErrorResource(exception.getClass().getSimpleName(), exception.getMessage());
  }

  @ExceptionHandler(AntragstellerNotFoundException.class)
  ResponseEntity<Object> handleAntragstellerNotFoundException(
      AntragstellerNotFoundException ex, WebRequest request) {

    final ErrorResource errorResource = buildResource(ex);
    return handleExceptionInternal(ex, errorResource, new HttpHeaders(), NOT_FOUND, request);
  }

  @ExceptionHandler(InvariantException.class)
  ResponseEntity<Object> handleInvariantException(
          InvariantException ex, WebRequest request) {

    final ErrorResource errorResource = buildResource(ex);
    return handleExceptionInternal(ex, errorResource, new HttpHeaders(), BAD_REQUEST, request);
  }


}
