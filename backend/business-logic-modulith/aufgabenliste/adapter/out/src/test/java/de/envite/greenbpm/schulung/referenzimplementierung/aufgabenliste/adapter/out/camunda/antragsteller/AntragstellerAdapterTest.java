package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.antragsteller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.AntragstellerId;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.in.Antragstellerabfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenAntragsteller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AntragstellerAdapterTest {

  private Antragstellerabfrage antragstellerabfrageMock;
  private AntragstellerMapper antragstellerMapperMock;
  private AntragstellerAdapter classUnderTest;

  @BeforeEach
  void setUp() {
    antragstellerabfrageMock = mock(Antragstellerabfrage.class);
    antragstellerMapperMock = mock(AntragstellerMapper.class);
    classUnderTest = new AntragstellerAdapter(antragstellerabfrageMock, antragstellerMapperMock);
  }

  @Test
  void should_return_mapped_fachdaten_antragsteller() {

    String antragstellerReferenz = "c7ebfce0-9fe8-4e5b-8e9e-203ed69fee8d";
    Antragsteller antragstellerMock = mock(Antragsteller.class);
    when(antragstellerabfrageMock.abfragen(new AntragstellerId(antragstellerReferenz)))
        .thenReturn(antragstellerMock);

    FachdatenAntragsteller fachdatenAntragstellerMock = mock(FachdatenAntragsteller.class);
    when(antragstellerMapperMock.toDomain(antragstellerMock))
        .thenReturn(fachdatenAntragstellerMock);

    FachdatenAntragsteller result = classUnderTest.queryByReferenz(antragstellerReferenz);

    assertThat(result).isNotNull().isEqualTo(fachdatenAntragstellerMock);
  }
}
