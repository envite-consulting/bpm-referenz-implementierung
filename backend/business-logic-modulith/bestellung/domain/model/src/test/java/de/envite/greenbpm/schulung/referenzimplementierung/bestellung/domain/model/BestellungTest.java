package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

import io.github.domainprimitives.validation.InvariantException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BestellungTest {

  private final String validBestellungId = "6ea3b3bf-61a5-4373-b3cd-e5d173d2fcc5";
  private final String validAntragstellerId = "190e162e-5bfa-4ef0-b70f-fe127df798bb";
  private final String validFahrzeugreferenz = "626c2b81-32c5-4948-b3c7-a4721d88c66a";
  private final LocalDateTime validDatum = LocalDateTime.of(2023, 8, 1, 12, 0);
  private final Status validStatus = Status.ANGELEGT;

  @Test
  void should_create_bestellung_without_id() {

    LocalDateTime now = LocalDateTime.now();
    Status defaultStatus = Status.ANGELEGT;

    Bestellung bestellung =
        new Bestellung(
            new Antragstellerreferenz(validAntragstellerId),
            new Fahrzeugreferenz(validFahrzeugreferenz));

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(bestellung.getBestellungId()).isNotNull();
    softly
        .assertThat(bestellung.getAntragstellerreferenz().getValue())
        .isEqualTo(validAntragstellerId);
    softly.assertThat(bestellung.getFahrzeugreferenz().getValue()).isEqualTo(validFahrzeugreferenz);
    softly
        .assertThat(bestellung.getBestelldatum().getValue())
        .isCloseTo(now, within(2, ChronoUnit.SECONDS));
    softly.assertThat(bestellung.getStatus()).isEqualTo(defaultStatus);
    softly.assertAll();
  }

  @Test
  void should_create_bestellung_with_id() {
    Bestellung bestellung =
        new Bestellung(
            new BestellungId(validBestellungId),
            new Antragstellerreferenz(validAntragstellerId),
            new Fahrzeugreferenz(validFahrzeugreferenz),
            new Bestelldatum(validDatum),
            validStatus);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(bestellung.getBestellungId().getValue()).isEqualTo(validBestellungId);
    softly
        .assertThat(bestellung.getAntragstellerreferenz().getValue())
        .isEqualTo(validAntragstellerId);
    softly.assertThat(bestellung.getFahrzeugreferenz().getValue()).isEqualTo(validFahrzeugreferenz);
    softly.assertThat(bestellung.getBestelldatum().getValue()).isEqualTo(validDatum);
    softly.assertThat(bestellung.getStatus()).isEqualTo(validStatus);
    softly.assertAll();
  }

  @ParameterizedTest(name = "should_throw_InvariantException_when_{0}_is_null")
  @MethodSource("invalidFieldCombinations")
  void should_throw_InvariantException_when_required_fields_are_null(
      String fieldName,
      BestellungId bestellungId,
      Antragstellerreferenz antragstellerreferenz,
      Fahrzeugreferenz fahrzeugreferenz,
      Bestelldatum bestelldatum,
      Status status) {
    assertThatThrownBy(
            () ->
                new Bestellung(
                    bestellungId, antragstellerreferenz, fahrzeugreferenz, bestelldatum, status))
        .isInstanceOf(InvariantException.class)
        .hasMessageContaining(fieldName);
  }

  private Stream<Arguments> invalidFieldCombinations() {

    return Stream.of(
        Arguments.of(
            "Bestellung ID",
            null,
            new Antragstellerreferenz(validAntragstellerId),
            new Fahrzeugreferenz(validFahrzeugreferenz),
            new Bestelldatum(validDatum),
            Status.ANGELEGT),
        Arguments.of(
            "Antragstellerreferenz",
            new BestellungId(validBestellungId),
            null,
            new Fahrzeugreferenz(validFahrzeugreferenz),
            new Bestelldatum(validDatum),
            Status.ANGELEGT),
        Arguments.of(
            "Fahrzeugreferenz",
            new BestellungId(validBestellungId),
            new Antragstellerreferenz(validAntragstellerId),
            null,
            new Bestelldatum(validDatum),
            Status.ANGELEGT),
        Arguments.of(
            "Bestelldatum",
            new BestellungId(validBestellungId),
            new Antragstellerreferenz(validAntragstellerId),
            new Fahrzeugreferenz(validFahrzeugreferenz),
            null,
            Status.ANGELEGT),
        Arguments.of(
            "Status",
            new BestellungId(validBestellungId),
            new Antragstellerreferenz(validAntragstellerId),
            new Fahrzeugreferenz(validFahrzeugreferenz),
            new Bestelldatum(validDatum),
            null));
  }
}
