package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model;

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
class BestellungTest {

  private final String validAntragstellerId = "190e162e-5bfa-4ef0-b70f-fe127df798bb";
  private final String validFahrzeugreferenz = "626c2b81-32c5-4948-b3c7-a4721d88c66a";
  private final LocalDateTime validDatum = LocalDateTime.of(2023, 8, 1, 12, 0);
  private final Status validStatus = Status.ANGELEGT;

  @Test
  void should_create_bestellung_without_id() {

    Bestellung bestellung =
        new Bestellung(
            new Antragstellerreferenz(validAntragstellerId),
            new Fahrzeugreferenz(validFahrzeugreferenz),
            new Bestelldatum(validDatum),
            validStatus);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(bestellung.getBestellungId()).isNull();
    softly
        .assertThat(bestellung.getAntragstellerreferenz().getValue())
        .isEqualTo(validAntragstellerId);
    softly.assertThat(bestellung.getFahrzeugreferenz().getValue()).isEqualTo(validFahrzeugreferenz);
    softly.assertThat(bestellung.getBestelldatum().getValue()).isEqualTo(validDatum);
    softly.assertThat(bestellung.getStatus()).isEqualTo(validStatus);
    softly.assertAll();
  }

  @Test
  void should_create_bestellung_with_id() {
    String validBestellungId = "823d3518-6448-4d23-80b0-8ce1b00c75bc";
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

  @Test
  void should_throw_exception_when_bestellung_id_is_null_in_second_constructor() {
    assertThatThrownBy(
            () ->
                new Bestellung(
                    null,
                    new Antragstellerreferenz(validAntragstellerId),
                    new Fahrzeugreferenz(validFahrzeugreferenz),
                    new Bestelldatum(validDatum),
                    validStatus))
        .isInstanceOf(InvariantException.class);
  }

  @ParameterizedTest(name = "should_throw_InvariantException_when_{0}_is_null")
  @MethodSource("invalidFieldCombinations")
  void should_throw_InvariantException_when_required_fields_are_null(
          String fieldName,
          Antragstellerreferenz antragstellerreferenz,
          Fahrzeugreferenz fahrzeugreferenz,
          Bestelldatum bestelldatum,
          Status status
  ) {
    assertThatThrownBy(() -> new Bestellung(antragstellerreferenz, fahrzeugreferenz, bestelldatum, status))
            .isInstanceOf(InvariantException.class);
  }

  private Stream<Arguments> invalidFieldCombinations() {

    return Stream.of(
            Arguments.of("antragstellerreferenz",null, new Fahrzeugreferenz(validFahrzeugreferenz), new Bestelldatum(validDatum), Status.ANGELEGT),
            Arguments.of("fahrzeugreferenz",new Antragstellerreferenz(validAntragstellerId), null, new Bestelldatum(validDatum), Status.ANGELEGT),
            Arguments.of("bestelldatum",new Antragstellerreferenz(validAntragstellerId), new Fahrzeugreferenz(validFahrzeugreferenz), null, Status.ANGELEGT),
            Arguments.of("status",new Antragstellerreferenz(validAntragstellerId), new Fahrzeugreferenz(validFahrzeugreferenz), new Bestelldatum(validDatum), null)
    );
  }
}
