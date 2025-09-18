package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.fahrzeug;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenFahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.in.Fahrzeugabfrage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FahrzeugAdapterTest {

  private Fahrzeugabfrage fahrzeugabfrageMock;
  private FahrzeugMapper fahrzeugMapperMock;
  private FahrzeugAdapter classUnderTest;

  @BeforeEach
  void setUp() {
    fahrzeugabfrageMock = mock(Fahrzeugabfrage.class);
    fahrzeugMapperMock = mock(FahrzeugMapper.class);
    classUnderTest = new FahrzeugAdapter(fahrzeugabfrageMock, fahrzeugMapperMock);
  }

  @Test
  void should_return_mapped_fachdaten_fahrzeug() {

    String fahrzeugReferenz = "c7ebfce0-9fe8-4e5b-8e9e-203ed69fee8d";
    Fahrzeug fahrzeugMock = mock(Fahrzeug.class);
    when(fahrzeugabfrageMock.abfragen(new FahrzeugId(fahrzeugReferenz))).thenReturn(fahrzeugMock);

    FachdatenFahrzeug fachdatenFahrzeugMock = mock(FachdatenFahrzeug.class);
    when(fahrzeugMapperMock.toDomain(fahrzeugMock)).thenReturn(fachdatenFahrzeugMock);

    FachdatenFahrzeug result = classUnderTest.queryByReferenz(fahrzeugReferenz);

    assertThat(result).isNotNull().isEqualTo(fachdatenFahrzeugMock);
  }
}
