package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.Aufgabe;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.FolgeaufgabeVorschlag;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.FolgeaufgabeNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Folgeaufgabenermittlung;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.AufgabenQuery;
import java.time.Duration;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
@Slf4j
class FolgeaufgabeVorschlagDomainService implements Folgeaufgabenermittlung {

  private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(3);
  private static final Duration POLLING_INTERVAL = Duration.ofMillis(500);

  private static final Comparator<Aufgabe> NACH_PRIORITAET =
      Comparator.comparing(Aufgabe::getPrioritaet);

  private static final Comparator<Aufgabe> AELTERE_ZUERST =
      Comparator.comparing(Aufgabe::getErstelldatum, Comparator.reverseOrder());

  private final AufgabenQuery aufgabenQuery;

  @Override
  public CompletableFuture<FolgeaufgabeVorschlag> ermitteln(
      String vorgangId, Optional<String> letztesArbeitspaket, Optional<Long> timeoutInMillis) {

    Duration timeout = timeoutInMillis.map(Duration::ofMillis).orElse(DEFAULT_TIMEOUT);

    return letztesArbeitspaket
        .map(arbeitspaket -> mitPollingUndArbeitspaketPruefung(vorgangId, arbeitspaket, timeout))
        .orElseGet(() -> mitPolling(vorgangId, timeout));
  }

  private CompletableFuture<FolgeaufgabeVorschlag> mitPolling(String vorgangId, Duration timeout) {
    return polling(vorgangId, NACH_PRIORITAET.thenComparing(AELTERE_ZUERST))
        .next()
        .timeout(timeout, Mono.error(() -> keineAufgabeGefunden(vorgangId)))
        .toFuture();
  }

  private CompletableFuture<FolgeaufgabeVorschlag> mitPollingUndArbeitspaketPruefung(
      String vorgangId, String arbeitspaket, Duration timeout) {

    AtomicReference<FolgeaufgabeVorschlag> letzterVorschlag = new AtomicReference<>();

    Comparator<Aufgabe> reihenfolge =
        NACH_PRIORITAET
            .thenComparing(aufgabe -> passtZuArbeitspaket(aufgabe, arbeitspaket))
            .thenComparing(AELTERE_ZUERST);

    return polling(vorgangId, reihenfolge)
        .doOnNext(letzterVorschlag::set)
        .filter(vorschlag -> arbeitspaket.equals(vorschlag.getArbeitspaket()))
        .next()
        .timeout(
            timeout,
            Mono.defer(
                () -> {
                  FolgeaufgabeVorschlag fallback = letzterVorschlag.get();
                  if (fallback != null) {
                    log.debug("Timeout erreicht: Vorschlag ohne passendes Arbeitspaket.");
                    return Mono.just(fallback);
                  }
                  return Mono.error(keineAufgabeGefunden(vorgangId));
                }))
        .toFuture();
  }

  private static boolean passtZuArbeitspaket(Aufgabe aufgabe, String arbeitspaket) {
    return arbeitspaket.equals(aufgabe.getArbeitspaket());
  }

  private Flux<FolgeaufgabeVorschlag> polling(String vorgangId, Comparator<Aufgabe> reihenfolge) {
    return Flux.interval(Duration.ZERO, POLLING_INTERVAL)
        .onBackpressureDrop()
        .concatMap(tick -> ermittleVorschlag(vorgangId, reihenfolge), 1)
        .doOnError(e -> log.error("Unerwarteter Fehler beim Ermitteln der Folgeaufgabe", e));
  }

  private Mono<FolgeaufgabeVorschlag> ermittleVorschlag(
      String vorgangId, Comparator<Aufgabe> reihenfolge) {
    return Mono.fromCallable(
            () ->
                aufgabenQuery.queryAllByProzessinstanz(vorgangId).stream()
                    .max(reihenfolge)
                    .map(this::erstelleVorschlag))
        .subscribeOn(Schedulers.boundedElastic())
        .flatMap(Mono::justOrEmpty);
  }

  private FolgeaufgabeVorschlag erstelleVorschlag(Aufgabe aufgabe) {
    return new FolgeaufgabeVorschlag(
        aufgabe.getId(), aufgabe.getVorgangId(), aufgabe.getArbeitspaket());
  }

  private FolgeaufgabeNotFoundException keineAufgabeGefunden(String vorgangId) {
    return new FolgeaufgabeNotFoundException(
        "Zum Vorgang %s wurde keine Folgeaufgabe gefunden.".formatted(vorgangId));
  }
}
