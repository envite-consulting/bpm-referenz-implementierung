package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang;

import static org.mockito.Mockito.mock;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.Fachdaten;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenAntragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenFahrzeug;
import io.github.domainprimitives.validation.InvariantException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VorgangTest {

  @Test
  void should_create_valid_vorgang_without_fachdaten() {

    Vorgang vorgang = new Vorgang("ID", "My Business Key");

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(vorgang.getId()).isNotNull().isEqualTo("ID");
    softly.assertThat(vorgang.getFachlicherSchluessel()).isNotNull().isEqualTo("My Business Key");
    softly.assertThat(vorgang.getFachdaten()).isNull();
    softly.assertAll();
  }

  @Test
  void should_add_fachdaten() {

    Vorgang vorgang = new Vorgang("ID-123", "Business-456");
    Fachdaten fachdaten =
        new Fachdaten(mock(FachdatenAntragsteller.class), mock(FachdatenFahrzeug.class));

    vorgang.fachdatenErgaenzen(fachdaten);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(vorgang.getId()).isEqualTo("ID-123");
    softly.assertThat(vorgang.getFachlicherSchluessel()).isEqualTo("Business-456");
    softly.assertThat(vorgang.getFachdaten()).isNotNull().isEqualTo(fachdaten);
    softly.assertAll();
  }

  @Test
  void should_throw_InvariantException_when_required_fields_are_null() {

    SoftAssertions softly = new SoftAssertions();

    softly
        .assertThatThrownBy(() -> new Vorgang(null, "key"))
        .isInstanceOf(InvariantException.class)
        .hasMessageContaining("ID");
    softly
        .assertThatThrownBy(() -> new Vorgang("id", null))
        .isInstanceOf(InvariantException.class)
        .hasMessageContaining("Fachlicher Schluessel");
  }
}
