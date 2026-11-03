package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.vorgang;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.Vorgang;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Folgeaufgabenermittlung;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Vorgangabfrage;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("vorgang")
class VorgangController {

  private final Vorgangabfrage vorgangabfrage;
  private final Folgeaufgabenermittlung folgeaufgabenermittlung;

  private final VorgangabfrageRestMapper vorgangabfrageMapper;
  private final FolgeaufgabeVorschlagRestMapper folgeaufgabeVorschlagMapper;

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

  @GetMapping("/{vorgangId}/folgeaufgabe-vorschlag")
  public CompletableFuture<FolgeaufgabeVorschlagResource> folgeaufgabeVorschlagen(
      @PathVariable String vorgangId,
      @RequestParam Optional<String> letztesArbeitspaket,
      @RequestParam Optional<Long> timeoutInMillis) {

    return folgeaufgabenermittlung
        .ermitteln(vorgangId, letztesArbeitspaket, timeoutInMillis)
        .thenApply(folgeaufgabeVorschlagMapper::toResource);
  }
}
