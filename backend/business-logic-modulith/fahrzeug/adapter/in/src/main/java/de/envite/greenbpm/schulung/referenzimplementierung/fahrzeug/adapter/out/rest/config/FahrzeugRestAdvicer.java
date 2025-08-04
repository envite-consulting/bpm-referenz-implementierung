package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.rest.config;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class FahrzeugRestAdvicer extends ResponseEntityExceptionHandler {

  record ErrorResource(String name, String errorMessage) {}

  private ErrorResource buildResource(RuntimeException exception) {
    return new ErrorResource(exception.getClass().getSimpleName(), exception.getMessage());
  }

  @ExceptionHandler(FahrzeugNotFoundException.class)
  ResponseEntity<Object> handleFahrzeugNotFoundException(
      FahrzeugNotFoundException ex, WebRequest request) {

    final ErrorResource errorResource = buildResource(ex);
    return handleExceptionInternal(ex, errorResource, new HttpHeaders(), NOT_FOUND, request);
  }
}
