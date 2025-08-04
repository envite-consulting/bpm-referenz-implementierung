package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest.config;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest.BestellungResource;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception.BestellungNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("error")
class RestAdviceSampleController {

  @GetMapping("/bestellungNotFoundException")
  public ResponseEntity<BestellungResource> throwBestellungNotFoundException() {
    throw new BestellungNotFoundException("Das ist ein Test");
  }
}
