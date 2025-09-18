package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.aufgabe;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.Aufgabe;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Aufgabenabfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Aufgabenverwaltung;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("aufgabe")
class AufgabenController {

  private final Aufgabenabfrage aufgabenabfrage;
  private final Aufgabenverwaltung aufgabenverwaltung;

  private final AufgabenabfrageRestMapper aufgabenabfrageMapper;

  @GetMapping
  public ResponseEntity<List<AufgabenabfrageResource>> abfragenAlleZuVorgang(
      @RequestParam String vorgangId) {
    List<Aufgabe> aufgaben = aufgabenabfrage.abfragenAlleZuVorgang(vorgangId);

    return ResponseEntity.ok(aufgaben.stream().map(aufgabenabfrageMapper::toResource).toList());
  }

  @GetMapping("/{aufgabenId}")
  public ResponseEntity<AufgabenabfrageResource> abfragen(@PathVariable String aufgabenId) {

    Aufgabe aufgabe = aufgabenabfrage.abfragen(aufgabenId);

    return ResponseEntity.ok(aufgabenabfrageMapper.toResource(aufgabe));
  }

  @PutMapping("/{aufgabenId}/abschliessenMitVariablen")
  public ResponseEntity<Void> abschliessenMitVariablen(
      @PathVariable String aufgabenId,
      @RequestBody AufgabeAbschliessenRequest aufgabeAbschliessenRequest) {

    aufgabenverwaltung.abschliessenMitVariablen(aufgabenId, aufgabeAbschliessenRequest.variables());

    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{aufgabenId}/uebernehmen")
  public ResponseEntity<Void> uebernehmen(
      @PathVariable String aufgabenId,
      @RequestBody AufgabeUebernehmenRequest aufgabeUebernehmenRequest) {

    aufgabenverwaltung.uebernehmen(aufgabenId, aufgabeUebernehmenRequest.userId());

    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{aufgabenId}/abgeben")
  public ResponseEntity<Void> abgeben(@PathVariable String aufgabenId) {

    aufgabenverwaltung.abgeben(aufgabenId);

    return ResponseEntity.noContent().build();
  }
}
