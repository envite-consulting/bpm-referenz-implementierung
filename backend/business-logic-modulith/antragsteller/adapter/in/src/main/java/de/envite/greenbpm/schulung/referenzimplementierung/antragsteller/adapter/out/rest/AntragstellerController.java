package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.in.Antragstellerabfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.AntragstellerId;
import java.util.List;
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

  private final Antragstellerabfrage antragstellerabfrage;
  private final AntragstellerRestMapper antragstellerMapper;

  @GetMapping("/{antragstellerId}")
  public ResponseEntity<AntragstellerResource> anzeigen(@PathVariable String antragstellerId) {
    Antragsteller antragsteller =
        antragstellerabfrage.abfragen(new AntragstellerId(antragstellerId));
    return ResponseEntity.ok(antragstellerMapper.toResource(antragsteller));
  }

  @GetMapping
  public ResponseEntity<List<AntragstellerResource>> anzeigenAlle() {

    List<AntragstellerResource> antragsteller =
        antragstellerabfrage.abfragenAlle().stream().map(antragstellerMapper::toResource).toList();

    return ResponseEntity.ok(antragsteller);
  }
}
