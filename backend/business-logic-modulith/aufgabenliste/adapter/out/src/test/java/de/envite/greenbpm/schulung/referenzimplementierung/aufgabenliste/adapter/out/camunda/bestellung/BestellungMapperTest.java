package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.bestellung;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenReferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Antragstellerreferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Fahrzeugreferenz;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class BestellungMapperTest {

  private final BestellungMapper classUnderTest = Mappers.getMapper(BestellungMapper.class);

  @Test
  void should_map_all_fields_to_domain() {

    Bestellung bestellung = mock(Bestellung.class);
    when(bestellung.getAntragstellerreferenz())
        .thenReturn(new Antragstellerreferenz("e8a055d2-3ef7-4f07-acb2-90f3f0638d47"));
    when(bestellung.getFahrzeugreferenz())
        .thenReturn(new Fahrzeugreferenz("e460ae54-e992-42a6-b21c-cba229baab77"));

    FachdatenReferenz fachdatenReferenz = classUnderTest.toDomain(bestellung);

    SoftAssertions softly = new SoftAssertions();
    softly
        .assertThat(fachdatenReferenz.getAntragstellerreferenz())
        .isEqualTo("e8a055d2-3ef7-4f07-acb2-90f3f0638d47");
    softly
        .assertThat(fachdatenReferenz.getFahrzeugreferenz())
        .isEqualTo("e460ae54-e992-42a6-b21c-cba229baab77");
    softly.assertAll();
  }
}
