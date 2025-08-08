package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model;

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
class FahrzeugTest {

  private final String validHersteller = "Audi";
  private final String validModell = "A4";
  private final int validJahr = 2020;

  @Test
  void should_create_fahrzeug_without_id() {
    Fahrzeug fahrzeug =
        new Fahrzeug(new Hersteller(validHersteller), new Modell(validModell), new Jahr(validJahr));

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(fahrzeug.getFahrzeugId()).isNull();
    softly.assertThat(fahrzeug.getHersteller().getValue()).isEqualTo(validHersteller);
    softly.assertThat(fahrzeug.getModell().getValue()).isEqualTo(validModell);
    softly.assertThat(fahrzeug.getJahr().getValue()).isEqualTo(validJahr);
    softly.assertAll();
  }

  @Test
  void should_set_fahrzeug_id_successfully() {
    Fahrzeug fahrzeug =
        new Fahrzeug(new Hersteller(validHersteller), new Modell(validModell), new Jahr(validJahr));

    String id = "a9950420-44c0-4ee8-aaf1-8122dd080900";
    fahrzeug.setFahrzeugId(new FahrzeugId(id));

    assertThat(fahrzeug.getFahrzeugId().getValue()).isEqualTo(id);
  }

  @ParameterizedTest(name = "should_throw_InvariantException_when_{0}_is_null")
  @MethodSource("invalidFieldCombinations")
  void should_throw_InvariantException_when_required_fields_are_null(
      String fieldName, Hersteller hersteller, Modell modell, Jahr jahr) {
    Assertions.assertThatThrownBy(() -> new Fahrzeug(hersteller, modell, jahr))
        .isInstanceOf(InvariantException.class);
  }

  private Stream<Arguments> invalidFieldCombinations() {

    return Stream.of(
        Arguments.of("hersteller", null, new Modell(validModell), new Jahr(validJahr)),
        Arguments.of("modell", new Hersteller(validHersteller), null, new Jahr(validJahr)),
        Arguments.of("jahr", new Hersteller(validHersteller), new Modell(validModell), null));
  }
}
