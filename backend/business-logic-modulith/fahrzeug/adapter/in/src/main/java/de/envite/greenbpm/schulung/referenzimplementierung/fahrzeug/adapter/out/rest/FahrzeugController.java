package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.in.Fahrzeugabfrage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fahrzeug")
@RequiredArgsConstructor
class FahrzeugController {

  private final Fahrzeugabfrage fahrzeugabfrage;
  private final FahrzeugRestMapper fahrzeugMapper;

  @GetMapping("/{fahrzeugId}")
  public ResponseEntity<FahrzeugResource> anzeigen(@PathVariable String fahrzeugId) {
    Fahrzeug fahrzeug = fahrzeugabfrage.abfragen(new FahrzeugId(fahrzeugId));
    return ResponseEntity.ok(fahrzeugMapper.toResource(fahrzeug));
  }

  @GetMapping
  public ResponseEntity<List<FahrzeugResource>> anzeigenAlle() {

    List<FahrzeugResource> fahrzeuge =
        fahrzeugabfrage.abfragenAlle().stream().map(fahrzeugMapper::toResource).toList();

    return ResponseEntity.ok(fahrzeuge);
  }
}
