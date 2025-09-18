package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.config;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("error")
class RestAdviceSampleController {

  @GetMapping("/vorgangNotFoundException")
  public ResponseEntity<Object> throwVorgangNotFoundException() {
    throw new VorgangNotFoundException("Das ist ein Test");
  }

  @GetMapping("/vorgangQueryException")
  public ResponseEntity<Object> throwVorgangQueryException() {
    throw new VorgangQueryException("Fehler beim Abfragen", new RuntimeException("Ursache"));
  }

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

  @GetMapping("/prozessstartException")
  public ResponseEntity<Object> throwProzessstartException() {
    throw new ProzessstartException("Fehler beim Prozessstart", new RuntimeException("Ursache"));
  }

  @GetMapping("/runtimeException")
  public ResponseEntity<Object> throwRuntimeException() {
    throw new RuntimeException("Nicht gefangene Runtime Exception");
  }
}
