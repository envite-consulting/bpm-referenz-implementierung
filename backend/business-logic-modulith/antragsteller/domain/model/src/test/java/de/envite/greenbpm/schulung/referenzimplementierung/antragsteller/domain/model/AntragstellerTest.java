package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model;

import io.github.domainprimitives.validation.InvariantException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AntragstellerTest {

  private final String validVorname = "Test";
  private final String validNachname = "Name";
  private final String validAbteilung = "Test Abteilung";
  private final String id = "da5398bb-bc03-4d4d-a7d9-74554f9e02b9";

  @Test
  void should_create_antragsteller_without_id() {

    Antragsteller antragsteller =
        new Antragsteller(
                new AntragstellerId(id),
                new Vorname(validVorname),
                new Nachname(validNachname),
                new Abteilung(validAbteilung)
        );

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(antragsteller.getAntragstellerId().getValue()).isEqualTo(id);
    softly.assertThat(antragsteller.getVorname().getValue()).isEqualTo(validVorname);
    softly.assertThat(antragsteller.getNachname().getValue()).isEqualTo(validNachname);
    softly.assertThat(antragsteller.getAbteilung().getValue()).isEqualTo(validAbteilung);
    softly.assertAll();
  }

  @ParameterizedTest(name = "should_throw_InvariantException_when_{0}_is_null")
  @MethodSource("invalidFieldCombinations")
  void should_throw_InvariantException_when_required_fields_are_null(
      String fieldName, AntragstellerId antragstellerId, Vorname vorname, Nachname nachname, Abteilung abteilung) {
    assertThatThrownBy(() -> new Antragsteller(antragstellerId, vorname, nachname, abteilung))
        .isInstanceOf(InvariantException.class)
        .hasMessageContaining(fieldName);
  }

  private Stream<Arguments> invalidFieldCombinations() {

    return Stream.of(
        Arguments.of("Antragsteller ID", null, new Vorname(validVorname), new Nachname(validNachname), new Abteilung(validAbteilung)),
        Arguments.of("Vorname", new AntragstellerId(id), null, new Nachname(validNachname), new Abteilung(validAbteilung)),
        Arguments.of("Nachname", new AntragstellerId(id), new Vorname(validVorname), null, new Abteilung(validAbteilung)),
        Arguments.of("Abteilung", new AntragstellerId(id), new Vorname(validVorname), new Nachname(validNachname), null));
  }
}
