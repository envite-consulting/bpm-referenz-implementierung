package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten;

import static org.mockito.Mockito.mock;

import io.github.domainprimitives.validation.InvariantException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class FachdatenTest {

  @Test
  void should_create_valid_fachdaten() {

    FachdatenAntragsteller antragstellerMock = mock(FachdatenAntragsteller.class);
    FachdatenFahrzeug fahrzeugMock = mock(FachdatenFahrzeug.class);

    Fachdaten fachdaten = new Fachdaten(antragstellerMock, fahrzeugMock);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(fachdaten.getAntragsteller()).isNotNull().isEqualTo(antragstellerMock);
    softly.assertThat(fachdaten.getFahrzeug()).isNotNull().isEqualTo(fahrzeugMock);
    softly.assertAll();
  }

  @Test
  void should_throw_InvariantException_when_required_fields_are_null() {

    SoftAssertions softly = new SoftAssertions();
    softly
        .assertThatThrownBy(() -> new Fachdaten(null, mock(FachdatenFahrzeug.class)))
        .isInstanceOf(InvariantException.class)
        .hasMessageContaining("Fahrzeug");
    softly
        .assertThatThrownBy(() -> new Fachdaten(mock(FachdatenAntragsteller.class), null))
        .isInstanceOf(InvariantException.class)
        .hasMessageContaining("Fahrzeug");
  }
}
