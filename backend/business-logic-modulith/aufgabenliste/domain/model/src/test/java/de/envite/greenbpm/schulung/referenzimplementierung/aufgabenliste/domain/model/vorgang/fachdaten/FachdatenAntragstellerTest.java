package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten;

import io.github.domainprimitives.validation.InvariantException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class FachdatenAntragstellerTest {

  @Test
  void should_create_valid_fachdaten_antragsteller() {

    FachdatenAntragsteller fachdatenAntragsteller =
        new FachdatenAntragsteller("Test Vorname", "Test Nachname");

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(fachdatenAntragsteller.getVorname()).isNotNull().isEqualTo("Test Vorname");
    softly.assertThat(fachdatenAntragsteller.getNachname()).isNotNull().isEqualTo("Test Nachname");
    softly.assertAll();
  }

  @Test
  void should_throw_InvariantException_when_required_fields_are_null() {

    SoftAssertions softly = new SoftAssertions();
    softly
        .assertThatThrownBy(() -> new FachdatenAntragsteller(null, "Test Nachname"))
        .isInstanceOf(InvariantException.class)
        .hasMessageContaining("Vorname");

    softly
        .assertThatThrownBy(() -> new FachdatenAntragsteller("Test Vorname", null))
        .isInstanceOf(InvariantException.class)
        .hasMessageContaining("Nachname");
  }
}
