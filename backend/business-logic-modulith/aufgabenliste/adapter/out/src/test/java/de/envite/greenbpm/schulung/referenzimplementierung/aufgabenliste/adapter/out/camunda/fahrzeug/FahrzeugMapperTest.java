package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.fahrzeug;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenFahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Hersteller;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Modell;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class FahrzeugMapperTest {

  private final FahrzeugMapper classUnderTest = Mappers.getMapper(FahrzeugMapper.class);

  @Test
  void should_map_all_fields_to_domain() {

    Fahrzeug fahrzeug = mock(Fahrzeug.class);
    when(fahrzeug.getHersteller()).thenReturn(new Hersteller("Hersteller"));
    when(fahrzeug.getModell()).thenReturn(new Modell("Modell"));

    FachdatenFahrzeug fachdatenFahrzeug = classUnderTest.toDomain(fahrzeug);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(fachdatenFahrzeug.getHersteller()).isEqualTo("Hersteller");
    softly.assertThat(fachdatenFahrzeug.getModell()).isEqualTo("Modell");
    softly.assertAll();
  }
}
