package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.antragsteller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Nachname;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Vorname;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenAntragsteller;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class AntragstellerMapperTest {

  private final AntragstellerMapper classUnderTest = Mappers.getMapper(AntragstellerMapper.class);

  @Test
  void should_map_all_fields_to_domain() {

    Antragsteller antragsteller = mock(Antragsteller.class);
    when(antragsteller.getVorname()).thenReturn(new Vorname("Test Vorname"));
    when(antragsteller.getNachname()).thenReturn(new Nachname("Test Nachname"));

    FachdatenAntragsteller fachdatenAntragsteller = classUnderTest.toDomain(antragsteller);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(fachdatenAntragsteller.getVorname()).isEqualTo("Test Vorname");
    softly.assertThat(fachdatenAntragsteller.getNachname()).isEqualTo("Test Nachname");
    softly.assertAll();
  }

  @Test
  void should_return_null_when_source_is_null() {

    FachdatenAntragsteller result = classUnderTest.toDomain(null);

    assertThat(result).isNull();
  }
}
