package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten;

import io.github.domainprimitives.validation.InvariantException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class FachdatenReferenzTest {

  @Test
  void should_create_valid_fachdaten_referenz() {

    FachdatenReferenz fachdatenFahrzeug = new FachdatenReferenz("antragstellerRef", "fahrzeugRef");

    SoftAssertions softly = new SoftAssertions();
    softly
        .assertThat(fachdatenFahrzeug.getAntragstellerreferenz())
        .isNotNull()
        .isEqualTo("antragstellerRef");
    softly.assertThat(fachdatenFahrzeug.getFahrzeugreferenz()).isNotNull().isEqualTo("fahrzeugRef");
    softly.assertAll();
  }

  @Test
  void should_throw_InvariantException_when_required_fields_are_null() {

    SoftAssertions softly = new SoftAssertions();
    softly
        .assertThatThrownBy(() -> new FachdatenReferenz(null, "fahrzeugRef"))
        .isInstanceOf(InvariantException.class)
        .hasMessageContaining("Antragstellerreferenz");
    softly
        .assertThatThrownBy(() -> new FachdatenReferenz("antragstellerRef", null))
        .isInstanceOf(InvariantException.class)
        .hasMessageContaining("Fahrzeugreferenz");
  }
}
