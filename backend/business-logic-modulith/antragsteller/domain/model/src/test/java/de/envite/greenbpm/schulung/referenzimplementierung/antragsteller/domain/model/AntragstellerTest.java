package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.github.domainprimitives.validation.InvariantException;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AntragstellerTest {

  private final String validVorname = "Test";
  private final String validNachname = "Name";
  private final String validAbteilung = "Test Abteilung";

  @Test
  void should_create_antragsteller_without_id() {

    Antragsteller antragsteller =
        new Antragsteller(
            new Vorname(validVorname), new Nachname(validNachname), new Abteilung(validAbteilung));

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(antragsteller.getAntragstellerId()).isNull();
    softly.assertThat(antragsteller.getVorname().getValue()).isEqualTo(validVorname);
    softly.assertThat(antragsteller.getNachname().getValue()).isEqualTo(validNachname);
    softly.assertThat(antragsteller.getAbteilung().getValue()).isEqualTo(validAbteilung);
    softly.assertAll();
  }

  @Test
  void should_set_antragsteller_id_successfully() {
    Antragsteller antragsteller =
        new Antragsteller(
            new Vorname(validVorname), new Nachname(validNachname), new Abteilung(validAbteilung));

    String id = "da5398bb-bc03-4d4d-a7d9-74554f9e02b9";
    antragsteller.setAntragstellerId(new AntragstellerId(id));

    assertThat(antragsteller.getAntragstellerId().getValue()).isEqualTo(id);
  }

  @ParameterizedTest(name = "should_throw_InvariantException_when_{0}_is_null")
  @MethodSource("invalidFieldCombinations")
  void should_throw_InvariantException_when_required_fields_are_null(
      String fieldName, Vorname vorname, Nachname nachname, Abteilung abteilung) {
    Assertions.assertThatThrownBy(() -> new Antragsteller(vorname, nachname, abteilung))
        .isInstanceOf(InvariantException.class);
  }

  private Stream<Arguments> invalidFieldCombinations() {

    return Stream.of(
        Arguments.of("vorname", null, new Nachname(validNachname), new Abteilung(validAbteilung)),
        Arguments.of("nachname", new Vorname(validVorname), null, new Abteilung(validAbteilung)),
        Arguments.of("abteilung", new Vorname(validVorname), new Nachname(validNachname), null));
  }
}
