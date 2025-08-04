package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest.config;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception.BestellungNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class BestellungRestAdvicer extends ResponseEntityExceptionHandler {

  record ErrorResource(String name, String errorMessage) {}

  private ErrorResource buildResoruce(RuntimeException exception) {
    return new ErrorResource(exception.getClass().getSimpleName(), exception.getMessage());
  }

  @ExceptionHandler(BestellungNotFoundException.class)
  ResponseEntity<Object> handleBestellungNotFoundException(
      BestellungNotFoundException ex, WebRequest request) {

    final ErrorResource errorResource = buildResoruce(ex);
    return handleExceptionInternal(ex, errorResource, new HttpHeaders(), NOT_FOUND, request);
  }
}
