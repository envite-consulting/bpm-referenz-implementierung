package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.rest.config;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.rest.AntragstellerResource;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.exception.AntragstellerNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("error")
class RestAdviceSampleController {

  @GetMapping("/antragstellerNotFoundException")
  public ResponseEntity<AntragstellerResource> throwAntragstellerNotFoundException() {
    throw new AntragstellerNotFoundException("Das ist ein Test");
  }
}
