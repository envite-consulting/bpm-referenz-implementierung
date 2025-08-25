package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.config;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeQueryException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeUpdateException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("error")
class RestAdviceSampleController {

    @GetMapping("/aufgabeNotFoundException")
    public ResponseEntity<Object> throwAufgabeNotFoundException() {
        throw new AufgabeNotFoundException("Das ist ein Test");
    }

    @GetMapping("/aufgabeQueryException")
    public ResponseEntity<Object> throwAufgabeQueryException() {
        throw new AufgabeQueryException("Fehler beim Abfragen", new RuntimeException("Ursache"));
    }

    @GetMapping("/aufgabeUpdateException")
    public ResponseEntity<Object> throwAufgabeUpdateException() {
        throw new AufgabeUpdateException("Fehler beim Aktualisieren", new RuntimeException("Ursache"));
    }

    @GetMapping("/runtimeException")
    public ResponseEntity<Object> throwRuntimeException() {
        throw new RuntimeException("Nicht gefangene Runtime Exception");
    }
}

