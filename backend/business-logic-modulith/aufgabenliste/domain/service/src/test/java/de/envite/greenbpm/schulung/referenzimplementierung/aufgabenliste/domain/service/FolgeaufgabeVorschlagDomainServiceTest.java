package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.Aufgabe;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.FolgeaufgabeVorschlag;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeQueryException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.FolgeaufgabeNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.AufgabenQuery;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FolgeaufgabeVorschlagDomainServiceTest {

  private static final String VORGANG_ID = "vorgang1";
  private static final Duration MAX_WARTEZEIT = Duration.ofSeconds(5);
  private static final Optional<Long> KURZER_TIMEOUT = Optional.of(100L);
  private static final Optional<Long> LANGER_TIMEOUT = Optional.of(3000L);

  private final AufgabenQuery aufgabenQueryMock = mock(AufgabenQuery.class);

  private final FolgeaufgabeVorschlagDomainService classUnderTest =
      new FolgeaufgabeVorschlagDomainService(aufgabenQueryMock);

  private static Aufgabe aufgabe(String id, int prioritaet, String arbeitspaket) {
    return aufgabe(id, prioritaet, arbeitspaket, LocalDateTime.of(2025, 1, 1, 0, 0));
  }

  private static Aufgabe aufgabe(
      String id, int prioritaet, String arbeitspaket, LocalDateTime erstelldatum) {
    return new Aufgabe(
        id, "Name", null, erstelldatum, "Ref", prioritaet, VORGANG_ID, arbeitspaket);
  }

  private void assertVorschlag(CompletableFuture<FolgeaufgabeVorschlag> future, String aufgabeId) {
    assertThat(future)
        .succeedsWithin(MAX_WARTEZEIT)
        .extracting(FolgeaufgabeVorschlag::getAufgabeId)
        .isEqualTo(aufgabeId);
  }

  private void assertFehler(
      CompletableFuture<FolgeaufgabeVorschlag> future, Class<? extends Throwable> fehler) {
    assertThat(future)
        .failsWithin(MAX_WARTEZEIT)
        .withThrowableOfType(ExecutionException.class)
        .withCauseInstanceOf(fehler);
  }

  @Nested
  class OhneArbeitspaket {

    @Test
    void should_suggest_task_with_highest_priority() {
      when(aufgabenQueryMock.queryAllByProzessinstanz(VORGANG_ID))
          .thenReturn(
              List.of(aufgabe("niedrig", 10, null), aufgabe("hoch", 90, null), aufgabe("mittel", 50, null)));

      assertVorschlag(classUnderTest.ermitteln(VORGANG_ID, Optional.empty(), LANGER_TIMEOUT), "hoch");
    }

    @Test
    void should_suggest_older_task_when_priority_is_equal() {
      when(aufgabenQueryMock.queryAllByProzessinstanz(VORGANG_ID))
          .thenReturn(
              List.of(
                  aufgabe("neu", 50, null, LocalDateTime.of(2025, 6, 1, 0, 0)),
                  aufgabe("alt", 50, null, LocalDateTime.of(2025, 1, 1, 0, 0))));

      assertVorschlag(classUnderTest.ermitteln(VORGANG_ID, Optional.empty(), LANGER_TIMEOUT), "alt");
    }

    @Test
    void should_poll_until_task_exists() {
      when(aufgabenQueryMock.queryAllByProzessinstanz(VORGANG_ID))
          .thenReturn(List.of())
          .thenReturn(List.of(aufgabe("spaeter", 50, null)));

      assertVorschlag(
          classUnderTest.ermitteln(VORGANG_ID, Optional.empty(), LANGER_TIMEOUT), "spaeter");
      verify(aufgabenQueryMock, times(2)).queryAllByProzessinstanz(VORGANG_ID);
    }

    @Test
    void should_fail_with_not_found_when_no_task_appears_before_timeout() {
      when(aufgabenQueryMock.queryAllByProzessinstanz(VORGANG_ID)).thenReturn(List.of());

      assertFehler(
          classUnderTest.ermitteln(VORGANG_ID, Optional.empty(), KURZER_TIMEOUT),
          FolgeaufgabeNotFoundException.class);
    }

    @Test
    void should_fail_when_query_fails_unexpectedly() {
      when(aufgabenQueryMock.queryAllByProzessinstanz(VORGANG_ID))
          .thenThrow(new AufgabeQueryException("Fehler", new RuntimeException("Ursache")));

      assertFehler(
          classUnderTest.ermitteln(VORGANG_ID, Optional.empty(), LANGER_TIMEOUT),
          AufgabeQueryException.class);
    }
  }

  @Nested
  class MitArbeitspaket {

    private static final Optional<String> ARBEITSPAKET = Optional.of("FAHRZEUG");

    @Test
    void should_suggest_task_immediately_when_arbeitspaket_matches() {
      when(aufgabenQueryMock.queryAllByProzessinstanz(VORGANG_ID))
          .thenReturn(List.of(aufgabe("passend", 70, "FAHRZEUG"), aufgabe("anderes", 60, "VERTRAG")));

      assertVorschlag(classUnderTest.ermitteln(VORGANG_ID, ARBEITSPAKET, LANGER_TIMEOUT), "passend");
      verify(aufgabenQueryMock).queryAllByProzessinstanz(VORGANG_ID);
    }

    @Test
    void should_keep_polling_until_matching_task_has_highest_priority() {
      when(aufgabenQueryMock.queryAllByProzessinstanz(VORGANG_ID))
          .thenReturn(List.of(aufgabe("anderes", 60, "VERTRAG")))
          .thenReturn(List.of(aufgabe("passend", 70, "FAHRZEUG"), aufgabe("anderes", 60, "VERTRAG")));

      assertVorschlag(classUnderTest.ermitteln(VORGANG_ID, ARBEITSPAKET, LANGER_TIMEOUT), "passend");
    }

    @Test
    void should_fall_back_to_last_suggestion_on_timeout() {
      when(aufgabenQueryMock.queryAllByProzessinstanz(VORGANG_ID))
          .thenReturn(List.of(aufgabe("anderes", 60, "VERTRAG")));

      assertVorschlag(classUnderTest.ermitteln(VORGANG_ID, ARBEITSPAKET, KURZER_TIMEOUT), "anderes");
    }

    @Test
    void should_prefer_higher_priority_over_matching_arbeitspaket() {
      when(aufgabenQueryMock.queryAllByProzessinstanz(VORGANG_ID))
          .thenReturn(List.of(aufgabe("wichtig", 90, "VERTRAG"), aufgabe("passend", 40, "FAHRZEUG")));

      assertVorschlag(classUnderTest.ermitteln(VORGANG_ID, ARBEITSPAKET, KURZER_TIMEOUT), "wichtig");
    }

    @Test
    void should_prefer_matching_arbeitspaket_over_age_when_priority_is_equal() {
      when(aufgabenQueryMock.queryAllByProzessinstanz(VORGANG_ID))
          .thenReturn(
              List.of(
                  aufgabe("alt", 50, "VERTRAG", LocalDateTime.of(2025, 1, 1, 0, 0)),
                  aufgabe("passend", 50, "FAHRZEUG", LocalDateTime.of(2025, 6, 1, 0, 0))));

      assertVorschlag(classUnderTest.ermitteln(VORGANG_ID, ARBEITSPAKET, LANGER_TIMEOUT), "passend");
      verify(aufgabenQueryMock).queryAllByProzessinstanz(VORGANG_ID);
    }

    @Test
    void should_suggest_older_task_when_priority_and_arbeitspaket_are_equal() {
      when(aufgabenQueryMock.queryAllByProzessinstanz(VORGANG_ID))
          .thenReturn(
              List.of(
                  aufgabe("neu", 50, "FAHRZEUG", LocalDateTime.of(2025, 6, 1, 0, 0)),
                  aufgabe("alt", 50, "FAHRZEUG", LocalDateTime.of(2025, 1, 1, 0, 0))));

      assertVorschlag(classUnderTest.ermitteln(VORGANG_ID, ARBEITSPAKET, LANGER_TIMEOUT), "alt");
    }

    @Test
    void should_fail_with_not_found_when_no_task_exists_at_all() {
      when(aufgabenQueryMock.queryAllByProzessinstanz(VORGANG_ID)).thenReturn(List.of());

      assertFehler(
          classUnderTest.ermitteln(VORGANG_ID, ARBEITSPAKET, KURZER_TIMEOUT),
          FolgeaufgabeNotFoundException.class);
    }
  }
}
