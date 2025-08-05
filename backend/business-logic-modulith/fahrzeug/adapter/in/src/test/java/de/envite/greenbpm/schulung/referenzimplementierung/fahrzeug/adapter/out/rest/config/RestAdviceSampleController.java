package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.rest.config;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;
import io.github.domainprimitives.validation.InvariantException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("error")
class RestAdviceSampleController {

  @GetMapping("/fahrzeugNotFoundException")
  public ResponseEntity<Object> throwFahrzeugNotFoundException() {
    throw new FahrzeugNotFoundException("Das ist ein Test");
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
