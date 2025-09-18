package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.bestellung;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenReferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in.Bestellungsabfrage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BestellungAdapterTest {

  private Bestellungsabfrage bestellungsabfrageMock;
  private BestellungMapper bestellungMapperMock;
  private BestellungAdapter classUnderTest;

  @BeforeEach
  void setUp() {
    bestellungsabfrageMock = mock(Bestellungsabfrage.class);
    bestellungMapperMock = mock(BestellungMapper.class);
    classUnderTest = new BestellungAdapter(bestellungsabfrageMock, bestellungMapperMock);
  }

  @Test
  void should_return_mapped_fachdaten_referenzen() {

    String bestellungId = "86434450-3554-4adc-bb73-c69f441e7d0c";
    Bestellung bestellungMock = mock(Bestellung.class);
    when(bestellungsabfrageMock.abfragen(new BestellungId(bestellungId)))
        .thenReturn(bestellungMock);

    FachdatenReferenz fachdatenReferenzMock = mock(FachdatenReferenz.class);
    when(bestellungMapperMock.toDomain(bestellungMock)).thenReturn(fachdatenReferenzMock);

    FachdatenReferenz result = classUnderTest.queryByFachlicherSchluessel(bestellungId);
    assertThat(result).isNotNull().isEqualTo(fachdatenReferenzMock);
  }
}
