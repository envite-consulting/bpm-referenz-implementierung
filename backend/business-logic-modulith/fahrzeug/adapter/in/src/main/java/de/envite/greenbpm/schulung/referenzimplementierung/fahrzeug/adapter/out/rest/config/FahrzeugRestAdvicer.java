package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.rest.config;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
class FahrzeugRestAdvicer extends ResponseEntityExceptionHandler {

    static record ErrorResource(String name, String errorMessage) {

    }

    private ErrorResource buildResoruce(RuntimeException exception) {
        return new ErrorResource(
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );
    }

    @ExceptionHandler(FahrzeugNotFoundException.class)
    ResponseEntity<Object> handleFahrzeugNotFoundException(FahrzeugNotFoundException ex, WebRequest request) {
        final ErrorResource errorResource = buildResoruce(ex);
        return handleExceptionInternal(ex, errorResource, new HttpHeaders(), NOT_FOUND, request);
    }
}
