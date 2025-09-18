package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.Aufgabe;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.AufgabenCommand;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.AufgabenQuery;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AufgabeDomainServiceTest {

  private final AufgabenCommand aufgabenCommandMock = mock(AufgabenCommand.class);
  private final AufgabenQuery aufgabenQueryMock = mock(AufgabenQuery.class);

  private AufgabeDomainService classUnderTest;

  @BeforeEach
  void setUp() {
    classUnderTest = new AufgabeDomainService(aufgabenCommandMock, aufgabenQueryMock);
  }

  @Test
  void should_query_by_id() {
    String aufgabenId = "id123";
    Aufgabe expectedResult = mock(Aufgabe.class);
    when(aufgabenQueryMock.queryById(aufgabenId)).thenReturn(expectedResult);

    Aufgabe result = classUnderTest.abfragen(aufgabenId);

    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void should_query_all() {

    String vorgangId = "vorgangId";

    Aufgabe aufgabe1 = mock(Aufgabe.class);
    Aufgabe aufgabe2 = mock(Aufgabe.class);
    when(aufgabenQueryMock.queryAllByVorgang(vorgangId)).thenReturn(List.of(aufgabe1, aufgabe2));

    List<Aufgabe> result = classUnderTest.abfragenAlleZuVorgang(vorgangId);

    assertThat(result).containsExactly(aufgabe1, aufgabe2);
  }

  @Test
  void should_return_empty_list_when_no_aufgaben_exist() {

    String vorgangId = "vorgangId";

    when(aufgabenQueryMock.queryAllByVorgang(vorgangId)).thenReturn(List.of());

    List<Aufgabe> result = classUnderTest.abfragenAlleZuVorgang(vorgangId);

    assertThat(result).isEmpty();
  }

  @Test
  void should_complete_with_variables() {
    String aufgabenId = "id123";
    Map<String, Object> variables = Map.of("var1", "value1");

    classUnderTest.abschliessenMitVariablen(aufgabenId, variables);

    verify(aufgabenCommandMock).completeWithVariables(aufgabenId, variables);
  }

  @Test
  void should_claim_task() {
    String aufgabenId = "id123";
    String userId = "user123";

    classUnderTest.uebernehmen(aufgabenId, userId);

    verify(aufgabenCommandMock).claim(aufgabenId, userId);
  }

  @Test
  void should_unclaim_task() {
    String aufgabenId = "id123";

    classUnderTest.abgeben(aufgabenId);

    verify(aufgabenCommandMock).unclaim(aufgabenId);
  }
}
