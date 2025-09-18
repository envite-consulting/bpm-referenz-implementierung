package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.vorgang;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.Vorgang;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Vorgangabfrage;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("vorgang")
class VorgangController {

  private final Vorgangabfrage vorgangabfrage;

  private final VorgangabfrageRestMapper vorgangabfrageMapper;

  @GetMapping
  public ResponseEntity<List<VorgangabfrageResource>> abfragenAlle() {

    List<Vorgang> vorgaenge = vorgangabfrage.abfragenAlle();

    return ResponseEntity.ok(vorgaenge.stream().map(vorgangabfrageMapper::toResource).toList());
  }

  @GetMapping("/{vorgangId}")
  public ResponseEntity<VorgangabfrageResource> abfragen(@PathVariable String vorgangId) {

    Vorgang vorgang = vorgangabfrage.abfragen(vorgangId);

    return ResponseEntity.ok(vorgangabfrageMapper.toResource(vorgang));
  }
}
