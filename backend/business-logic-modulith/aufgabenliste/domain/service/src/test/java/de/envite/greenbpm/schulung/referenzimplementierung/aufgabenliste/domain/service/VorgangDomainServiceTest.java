package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.Vorgang;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenAntragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenFahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenReferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.FachdatenAntragstellerQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.FachdatenFahrzeugQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.FachdatenReferenzQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.VorgangQuery;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VorgangDomainServiceTest {

  private VorgangQuery vorgangQueryMock;

  private FachdatenReferenzQuery fachdatenReferenzQueryMock;

  private FachdatenAntragstellerQuery fachdatenAntragstellerQueryMock;

  private FachdatenFahrzeugQuery fachdatenFahrzeugQueryMock;

  private VorgangDomainService classUnderTest;

  @BeforeEach
  void setUp() {
    vorgangQueryMock = mock(VorgangQuery.class);
    fachdatenReferenzQueryMock = mock(FachdatenReferenzQuery.class);
    fachdatenAntragstellerQueryMock = mock(FachdatenAntragstellerQuery.class);
    fachdatenFahrzeugQueryMock = mock(FachdatenFahrzeugQuery.class);

    classUnderTest =
        new VorgangDomainService(
            vorgangQueryMock,
            fachdatenReferenzQueryMock,
            fachdatenAntragstellerQueryMock,
            fachdatenFahrzeugQueryMock);
  }

  @Test
  void should_return_vorgang_with_fachdaten_when_abfragen_by_id() {
    String vorgangId = "ID-1";
    String fachlicherSchluessel = "KEY-1";
    String antragstellerreferenz = "antragRef-1";
    String fahrzeugreferenz = "fahrzeugRef-1";

    Vorgang vorgang = new Vorgang(vorgangId, fachlicherSchluessel);

    FachdatenReferenz referenzMock = mock(FachdatenReferenz.class);
    when(referenzMock.getAntragstellerreferenz()).thenReturn(antragstellerreferenz);
    when(referenzMock.getFahrzeugreferenz()).thenReturn(fahrzeugreferenz);

    FachdatenAntragsteller antragstellerMock = mock(FachdatenAntragsteller.class);
    FachdatenFahrzeug fahrzeug = mock(FachdatenFahrzeug.class);

    when(vorgangQueryMock.queryById(vorgangId)).thenReturn(vorgang);
    when(fachdatenReferenzQueryMock.queryByFachlicherSchluessel(fachlicherSchluessel))
        .thenReturn(referenzMock);
    when(fachdatenAntragstellerQueryMock.queryByReferenz(antragstellerreferenz))
        .thenReturn(antragstellerMock);
    when(fachdatenFahrzeugQueryMock.queryByReferenz(fahrzeugreferenz)).thenReturn(fahrzeug);

    Vorgang result = classUnderTest.abfragen(vorgangId);

    assertThat(result.getFachdaten()).isNotNull();
    assertThat(result.getFachdaten().getAntragsteller()).isEqualTo(antragstellerMock);
    assertThat(result.getFachdaten().getFahrzeug()).isEqualTo(fahrzeug);
  }

  @Test
  void should_return_all_vorgaenge_with_fachdaten_when_abfragenAlle() {

    String fachlicherSchluessel1 = "KEY-1";
    String fachlicherSchluessel2 = "KEY-2";
    String antragstellerRef1 = "antragRef-1";
    String fahrzeugRef1 = "fahrzeugRef-1";
    String antragstellerRef2 = "antragRef-2";
    String fahrzeugRef2 = "fahrzeugRef-2";

    Vorgang vorgang1 = new Vorgang("ID-1", fachlicherSchluessel1);
    Vorgang vorgang2 = new Vorgang("ID-2", fachlicherSchluessel2);

    when(vorgangQueryMock.queryAll()).thenReturn(List.of(vorgang1, vorgang2));

    FachdatenReferenz referenzMock1 = mock(FachdatenReferenz.class);
    when(referenzMock1.getAntragstellerreferenz()).thenReturn(antragstellerRef1);
    when(referenzMock1.getFahrzeugreferenz()).thenReturn(fahrzeugRef1);

    FachdatenReferenz referenzMock2 = mock(FachdatenReferenz.class);
    when(referenzMock2.getAntragstellerreferenz()).thenReturn(antragstellerRef2);
    when(referenzMock2.getFahrzeugreferenz()).thenReturn(fahrzeugRef2);

    FachdatenAntragsteller antragstellerMock1 = mock(FachdatenAntragsteller.class);
    FachdatenFahrzeug fahrzeugMock1 = mock(FachdatenFahrzeug.class);

    FachdatenAntragsteller antragstellerMock2 = mock(FachdatenAntragsteller.class);
    FachdatenFahrzeug fahrzeugMock2 = mock(FachdatenFahrzeug.class);

    when(fachdatenReferenzQueryMock.queryByFachlicherSchluessel(fachlicherSchluessel1))
        .thenReturn(referenzMock1);
    when(fachdatenReferenzQueryMock.queryByFachlicherSchluessel(fachlicherSchluessel2))
        .thenReturn(referenzMock2);

    when(fachdatenAntragstellerQueryMock.queryByReferenz(antragstellerRef1))
        .thenReturn(antragstellerMock1);
    when(fachdatenAntragstellerQueryMock.queryByReferenz(antragstellerRef2))
        .thenReturn(antragstellerMock2);

    when(fachdatenFahrzeugQueryMock.queryByReferenz(fahrzeugRef1)).thenReturn(fahrzeugMock1);
    when(fachdatenFahrzeugQueryMock.queryByReferenz(fahrzeugRef2)).thenReturn(fahrzeugMock2);

    List<Vorgang> result = classUnderTest.abfragenAlle();

    assertThat(result).hasSize(2);

    Vorgang resultVorgang1 = result.getFirst();
    assertThat(resultVorgang1.getFachdaten()).isNotNull();
    assertThat(resultVorgang1.getFachdaten().getAntragsteller()).isEqualTo(antragstellerMock1);
    assertThat(resultVorgang1.getFachdaten().getFahrzeug()).isEqualTo(fahrzeugMock1);

    Vorgang resultVorgang2 = result.get(1);
    assertThat(resultVorgang2.getFachdaten()).isNotNull();
    assertThat(resultVorgang2.getFachdaten().getAntragsteller()).isEqualTo(antragstellerMock2);
    assertThat(resultVorgang2.getFachdaten().getFahrzeug()).isEqualTo(fahrzeugMock2);
  }
}
