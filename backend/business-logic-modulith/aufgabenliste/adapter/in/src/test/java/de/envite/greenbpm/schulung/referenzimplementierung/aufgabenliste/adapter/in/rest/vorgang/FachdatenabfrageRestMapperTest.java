package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.vorgang;

import static org.assertj.core.api.Assertions.assertThat;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.Fachdaten;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenAntragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenFahrzeug;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class FachdatenabfrageRestMapperTest {

  private final FachdatenabfrageRestMapper classUnderTest =
      Mappers.getMapper(FachdatenabfrageRestMapper.class);

  @Test
  void should_map_all_fields_to_resource() {
    FachdatenAntragsteller antragsteller = new FachdatenAntragsteller("Vorname", "Nachname");
    FachdatenFahrzeug fahrzeug = new FachdatenFahrzeug("Hersteller", "Modell");
    Fachdaten fachdaten = new Fachdaten(antragsteller, fahrzeug);

    FachdatenabfrageResource result = classUnderTest.toResource(fachdaten);

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result.antragstellerVorname()).isEqualTo("Vorname");
    softAssertions.assertThat(result.antragstellerNachname()).isEqualTo("Nachname");
    softAssertions.assertThat(result.fahrzeugHersteller()).isEqualTo("Hersteller");
    softAssertions.assertThat(result.fahrzeugModell()).isEqualTo("Modell");
    softAssertions.assertAll();
  }

  @Test
  void should_return_null_when_source_is_null() {

    FachdatenabfrageResource result = classUnderTest.toResource(null);

    assertThat(result).isNull();
  }
}
