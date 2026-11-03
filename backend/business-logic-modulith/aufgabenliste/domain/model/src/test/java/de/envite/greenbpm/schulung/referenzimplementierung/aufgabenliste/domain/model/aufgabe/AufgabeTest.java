package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.github.domainprimitives.validation.InvariantException;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AufgabeTest {

  private final String validAufgabeId = "123";
  private final String validName = "My Task";
  private final String validBearbeiter = "Test Benutzer";
  private final LocalDateTime validErstelldatum = LocalDateTime.now();
  private final String validFormularreferenz = "Ref1";
  private final Integer validPrioritaet = 50;
  private final String validVorgangId = "Vorgang1";
  private final String validArbeitspaket = "FAHRZEUG";

  @Test
  void should_create_valid_aufgabe() {

    Aufgabe aufgabe =
        new Aufgabe(
            validAufgabeId,
            validName,
            validBearbeiter,
            validErstelldatum,
            validFormularreferenz,
            validPrioritaet,
            validVorgangId,
            validArbeitspaket);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(aufgabe.getId()).isNotNull().isEqualTo(validAufgabeId);
    softly.assertThat(aufgabe.getName()).isNotNull().isEqualTo(validName);
    softly.assertThat(aufgabe.getBearbeiter()).isNotNull().isEqualTo(validBearbeiter);
    softly.assertThat(aufgabe.getErstelldatum()).isNotNull().isEqualTo(validErstelldatum);
    softly.assertThat(aufgabe.getFormularreferenz()).isNotNull().isEqualTo(validFormularreferenz);
    softly.assertThat(aufgabe.getPrioritaet()).isNotNull().isEqualTo(validPrioritaet);
    softly.assertThat(aufgabe.getVorgangId()).isNotNull().isEqualTo(validVorgangId);
    softly.assertThat(aufgabe.getArbeitspaket()).isNotNull().isEqualTo(validArbeitspaket);
    softly.assertAll();
  }

  @Test
  void should_create_valid_aufgabe_without_bearbeiter() {

    Aufgabe aufgabe =
        new Aufgabe(
            validAufgabeId,
            validName,
            null,
            validErstelldatum,
            validFormularreferenz,
            validPrioritaet,
            validVorgangId,
            null);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(aufgabe.getId()).isNotNull().isEqualTo(validAufgabeId);
    softly.assertThat(aufgabe.getName()).isNotNull().isEqualTo(validName);
    softly.assertThat(aufgabe.getBearbeiter()).isNull();
    softly.assertThat(aufgabe.getErstelldatum()).isNotNull().isEqualTo(validErstelldatum);
    softly.assertThat(aufgabe.getFormularreferenz()).isNotNull().isEqualTo(validFormularreferenz);
    softly.assertThat(aufgabe.getPrioritaet()).isNotNull().isEqualTo(validPrioritaet);
    softly.assertThat(aufgabe.getVorgangId()).isNotNull().isEqualTo(validVorgangId);
    softly.assertThat(aufgabe.getArbeitspaket()).isNull();
    softly.assertAll();
  }

  @ParameterizedTest(name = "should_throw_InvariantException_when_{0}_is_null")
  @MethodSource("invalidFieldCombinations")
  void should_throw_InvariantException_when_required_fields_are_null(
      String fieldName,
      String aufgabeId,
      String name,
      String bearbeiter,
      LocalDateTime erstelldatum,
      String formularreferenz,
      Integer prioritaet,
      String vorgangId) {
    assertThatThrownBy(
            () -> new Aufgabe(
                    aufgabeId, name, bearbeiter, erstelldatum, formularreferenz, prioritaet, vorgangId, null))
        .isInstanceOf(InvariantException.class)
        .hasMessageContaining(fieldName);
  }

  private Stream<Arguments> invalidFieldCombinations() {

    return Stream.of(
        Arguments.of(
            "Aufgabe ID",
            null,
            validName,
            validBearbeiter,
            validErstelldatum,
            validFormularreferenz,
            validPrioritaet,
            validVorgangId),
        Arguments.of(
            "Name",
            validAufgabeId,
            null,
            validBearbeiter,
            validErstelldatum,
            validFormularreferenz,
            validPrioritaet,
            validVorgangId),
        Arguments.of(
            "Erstelldatum",
            validAufgabeId,
            validName,
            validBearbeiter,
            null,
            validFormularreferenz,
            validPrioritaet,
            validVorgangId),
        Arguments.of(
            "Formularreferenz",
            validAufgabeId,
            validName,
            validBearbeiter,
            validErstelldatum,
            null,
            validPrioritaet,
            validVorgangId),
        Arguments.of(
            "Prioritaet",
            validAufgabeId,
            validName,
            validBearbeiter,
            validErstelldatum,
            validFormularreferenz,
            null,
            validVorgangId),
        Arguments.of(
            "Vorgang ID",
            validAufgabeId,
            validName,
            validBearbeiter,
            validErstelldatum,
            validFormularreferenz,
            validPrioritaet,
            null));
  }
}
