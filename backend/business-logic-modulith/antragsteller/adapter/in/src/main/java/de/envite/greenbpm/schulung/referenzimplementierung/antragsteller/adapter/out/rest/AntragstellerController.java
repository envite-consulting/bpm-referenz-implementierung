package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller.AntragstellerId;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.in.AntragstellerAbfrage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("antragsteller")
@RequiredArgsConstructor
class AntragstellerController {

  private final AntragstellerAbfrage antragstellerAbfrage;
  private final AntragstellerRestMapper antragstellerMapper;

  @GetMapping("/{antragstellerId}")
  public ResponseEntity<AntragstellerResource> anzeigen(@PathVariable String antragstellerId) {
    Antragsteller antragsteller =
        antragstellerAbfrage.abfragen(new AntragstellerId(antragstellerId));
    return ResponseEntity.ok(antragstellerMapper.toResource(antragsteller));
  }
}
