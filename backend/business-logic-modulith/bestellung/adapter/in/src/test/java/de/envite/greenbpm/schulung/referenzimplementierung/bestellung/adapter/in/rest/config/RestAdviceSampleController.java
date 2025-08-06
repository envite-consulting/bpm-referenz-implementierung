package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest.config;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception.BestellungNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception.BestellungPersistenceException;
import io.github.domainprimitives.validation.InvariantException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("error")
class RestAdviceSampleController {

  @GetMapping("/bestellungNotFoundException")
  public ResponseEntity<Object> throwBestellungNotFoundException() {
    throw new BestellungNotFoundException("Das ist ein Test");
  }

  @GetMapping("/bestellungPersistenceException")
  public ResponseEntity<Object> throwBestellungPersitenceException() {
    throw new BestellungPersistenceException("Fehler beim Speichern", new RuntimeException("Ursache"));
  }

  @GetMapping("/invariantException")
  public ResponseEntity<Object> throwInvariantException() {
    throw new InvariantException("Test", "Test should not be null");
  }

  @GetMapping("/runtimeException")
  public ResponseEntity<Object> throwRuntimeException() {
    throw new RuntimeException("Nicht gefangene Runtime Exception");
  }
}
