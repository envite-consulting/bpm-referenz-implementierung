package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.config;

import static org.springframework.http.HttpStatus.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.*;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class AufgabenlisteRestAdvicer extends ResponseEntityExceptionHandler {

  record ErrorResource(
      String name,
      String errorMessage,
      @JsonInclude(JsonInclude.Include.NON_EMPTY) Optional<String> cause) {}

  private ErrorResource buildResource(RuntimeException exception) {

    return new ErrorResource(
        exception.getClass().getSimpleName(), exception.getMessage(), getCauseMessage(exception));
  }

  private Optional<String> getCauseMessage(RuntimeException exception) {
    return Optional.ofNullable(exception.getCause()).map(Throwable::getMessage);
  }

  @ExceptionHandler(VorgangNotFoundException.class)
  ResponseEntity<Object> handleVorgangNotFoundException(
      VorgangNotFoundException ex, WebRequest request) {

    final ErrorResource errorResource = buildResource(ex);
    return handleExceptionInternal(ex, errorResource, new HttpHeaders(), NOT_FOUND, request);
  }

  @ExceptionHandler(VorgangQueryException.class)
  public ResponseEntity<Object> handleVorgangQueryException(
      VorgangQueryException ex, WebRequest request) {

    final ErrorResource errorResource = buildResource(ex);
    return handleExceptionInternal(
        ex, errorResource, new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
  }

  @ExceptionHandler(AufgabeNotFoundException.class)
  ResponseEntity<Object> handleAufgabeNotFoundException(
      AufgabeNotFoundException ex, WebRequest request) {

    final ErrorResource errorResource = buildResource(ex);
    return handleExceptionInternal(ex, errorResource, new HttpHeaders(), NOT_FOUND, request);
  }

  @ExceptionHandler(AufgabeQueryException.class)
  public ResponseEntity<Object> handleAufgabeQueryException(
      AufgabeQueryException ex, WebRequest request) {

    final ErrorResource errorResource = buildResource(ex);
    return handleExceptionInternal(
        ex, errorResource, new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
  }

  @ExceptionHandler(AufgabeUpdateException.class)
  ResponseEntity<Object> handleAufgabeUpdateException(
      AufgabeUpdateException ex, WebRequest request) {

    final ErrorResource errorResource = buildResource(ex);
    return handleExceptionInternal(
        ex, errorResource, new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
  }

  @ExceptionHandler(ProzessstartException.class)
  public ResponseEntity<Object> handleProzessstartException(
      ProzessstartException ex, WebRequest request) {

    final ErrorResource errorResource = buildResource(ex);
    return handleExceptionInternal(
        ex, errorResource, new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
  }
}
