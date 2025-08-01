package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.rest.config;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception.BestellungNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BestellungNotFoundException.class)
    ResponseEntity<Object> handleBestellungNotFound(RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), NOT_FOUND, request);
    }
}