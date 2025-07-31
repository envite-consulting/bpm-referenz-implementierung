package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.rest.resource.BestellungResource;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.usecase.in.BestellungsAbfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.usecase.in.BestellungsErfassung;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("bestellung/")
public class BestellungController {

  private final BestellungsAbfrage bestellungsAbfrage;
  private final BestellungsErfassung bestellungsErfassung;
  private final BestellungRestMapper bestellungMapper;

  @PostMapping("/")
  public ResponseEntity<BestellungResource> erfassen(
      @RequestBody BestellungResource bestellungResource) {

    Bestellung bestellung =
        bestellungsErfassung.erfassen(bestellungMapper.toDomain(bestellungResource));

    return ResponseEntity.ok(bestellungMapper.toResource(bestellung));
  }

  @GetMapping("/{bestellungId}")
  public ResponseEntity<BestellungResource> anzeigen(@PathVariable Long bestellungId) {

    Bestellung bestellung = bestellungsAbfrage.abfragen(new BestellungId(bestellungId));

    return ResponseEntity.ok(bestellungMapper.toResource(bestellung));
  }
}
