package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten;

import io.github.domainprimitives.validation.InvariantException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class FachdatenFahrzeugTest {

  @Test
  void should_create_valid_fachdaten_fahrzeug() {

    FachdatenFahrzeug fachdatenFahrzeug = new FachdatenFahrzeug("Test Hersteller", "Test Modell");

    SoftAssertions softly = new SoftAssertions();
    softly
        .assertThat(fachdatenFahrzeug.getHersteller())
        .isNotNull()
        .isEqualTo("Test Hersteller");
    softly.assertThat(fachdatenFahrzeug.getModell()).isNotNull().isEqualTo("Test Modell");
    softly.assertAll();
  }

  @Test
  void should_throw_InvariantException_when_required_fields_are_null() {

    SoftAssertions softly = new SoftAssertions();
    softly
        .assertThatThrownBy(() -> new FachdatenFahrzeug(null, "Test Modell"))
        .isInstanceOf(InvariantException.class)
        .hasMessageContaining("Hersteller");
    softly
        .assertThatThrownBy(() -> new FachdatenFahrzeug("Test Hersteller", null))
        .isInstanceOf(InvariantException.class)
        .hasMessageContaining("Modell");
  }
}
