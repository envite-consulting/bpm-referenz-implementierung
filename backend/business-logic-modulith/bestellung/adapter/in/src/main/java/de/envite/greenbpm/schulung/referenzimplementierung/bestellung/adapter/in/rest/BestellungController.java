package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in.Bestellungsabfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in.Bestellungserfassung;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("bestellung")
class BestellungController {

  private final Bestellungserfassung bestellungserfassung;
  private final Bestellungsabfrage bestellungsabfrage;
  private final BestellungserfassungRestMapper bestellungserfassungMapper;
  private final BestellungsabfrageRestMapper bestellungsabfrageMapper;

  @PostMapping
  public ResponseEntity<BestellungsabfrageResource> erfassen(
      @RequestBody BestellungserfassungResource bestellungserfassungResource) {

    Bestellung bestellung =
        bestellungserfassung.erfassen(
            bestellungserfassungMapper.toDomain(bestellungserfassungResource));

    return ResponseEntity.ok(bestellungsabfrageMapper.toResource(bestellung));
  }

  @GetMapping("/{bestellungId}")
  public ResponseEntity<BestellungsabfrageResource> abfragen(@PathVariable String bestellungId) {

    Bestellung bestellung = bestellungsabfrage.abfragen(new BestellungId(bestellungId));

    return ResponseEntity.ok(bestellungsabfrageMapper.toResource(bestellung));
  }
}
