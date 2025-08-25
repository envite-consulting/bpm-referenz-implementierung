package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model;

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

  @Test
  void should_create_valid_aufgabe() {

    Aufgabe aufgabe =
        new Aufgabe(
            validAufgabeId, validName, validBearbeiter, validErstelldatum, validFormularreferenz);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(aufgabe.getId()).isNotNull().isEqualTo(validAufgabeId);
    softly.assertThat(aufgabe.getName()).isNotNull().isEqualTo(validName);
    softly.assertThat(aufgabe.getBearbeiter()).isNotNull().isEqualTo(validBearbeiter);
    softly.assertThat(aufgabe.getErstelldatum()).isNotNull().isEqualTo(validErstelldatum);
    softly.assertThat(aufgabe.getFormularreferenz()).isNotNull().isEqualTo(validFormularreferenz);
    softly.assertAll();
  }

  @Test
  void should_create_valid_aufgabe_without_bearbeiter() {

    Aufgabe aufgabe =
        new Aufgabe(validAufgabeId, validName, null, validErstelldatum, validFormularreferenz);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(aufgabe.getId()).isNotNull().isEqualTo(validAufgabeId);
    softly.assertThat(aufgabe.getName()).isNotNull().isEqualTo(validName);
    softly.assertThat(aufgabe.getBearbeiter()).isNull();
    softly.assertThat(aufgabe.getErstelldatum()).isNotNull().isEqualTo(validErstelldatum);
    softly.assertThat(aufgabe.getFormularreferenz()).isNotNull().isEqualTo(validFormularreferenz);
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
      String formularreferenz) {
    assertThatThrownBy(
            () -> new Aufgabe(aufgabeId, name, bearbeiter, erstelldatum, formularreferenz))
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
            validFormularreferenz),
        Arguments.of(
            "Name",
            validAufgabeId,
            null,
            validBearbeiter,
            validErstelldatum,
            validFormularreferenz),
        Arguments.of(
            "Erstelldatum",
            validAufgabeId,
            validName,
            validBearbeiter,
            null,
            validFormularreferenz),
        Arguments.of(
            "Formularreferenz", validAufgabeId, validName, validBearbeiter, validErstelldatum, null));
  }
}
