package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest.config;

import static org.springframework.http.HttpStatus.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception.BestellungNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception.BestellungPersistenceException;
import io.github.domainprimitives.validation.InvariantException;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class BestellungRestAdvicer extends ResponseEntityExceptionHandler {

  record ErrorResource(String name, String errorMessage, @JsonInclude(JsonInclude.Include.NON_EMPTY) Optional<String> cause) {}

  private ErrorResource buildResource(RuntimeException exception) {

    return new ErrorResource(
            exception.getClass().getSimpleName(),
            exception.getMessage(),
            getCauseMessage(exception)
    );
  }

  private Optional<String> getCauseMessage(RuntimeException exception) {
    return Optional.ofNullable(exception.getCause())
            .map(Throwable::getMessage);
  }

  @ExceptionHandler(BestellungNotFoundException.class)
  ResponseEntity<Object> handleBestellungNotFoundException(
      BestellungNotFoundException ex, WebRequest request) {

    final ErrorResource errorResource = buildResource(ex);
    return handleExceptionInternal(ex, errorResource, new HttpHeaders(), NOT_FOUND, request);
  }

  @ExceptionHandler(BestellungPersistenceException.class)
  public ResponseEntity<Object> handleBestellungPersistenceException(
      BestellungPersistenceException ex, WebRequest request) {

    final ErrorResource errorResource = buildResource(ex);
    return handleExceptionInternal(
        ex, errorResource, new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
  }

  @ExceptionHandler(InvariantException.class)
  ResponseEntity<Object> handleInvariantException(InvariantException ex, WebRequest request) {

    final ErrorResource errorResource = buildResource(ex);
    return handleExceptionInternal(ex, errorResource, new HttpHeaders(), BAD_REQUEST, request);
  }
}
