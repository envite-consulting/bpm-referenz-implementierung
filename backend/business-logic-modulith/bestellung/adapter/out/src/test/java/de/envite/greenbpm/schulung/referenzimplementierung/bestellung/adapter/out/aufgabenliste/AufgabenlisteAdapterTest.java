package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.aufgabenliste;

import static org.mockito.Mockito.*;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Prozessverwaltung;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AufgabenlisteAdapterTest {

  private Prozessverwaltung prozessverwaltungMock;
  private AufgabenlisteAdapter classUnderTest;

  @BeforeEach
  void setUp() {
    prozessverwaltungMock = mock(Prozessverwaltung.class);
    classUnderTest = new AufgabenlisteAdapter(prozessverwaltungMock);
  }

  @Test
  void should_start_process_with_correct_parameters() {

    String prozessReferenz = "ref1";
    Map<String, Object> variables = Map.of("var1", "value1");

    classUnderTest.start(prozessReferenz, variables);

    verify(prozessverwaltungMock).starten(prozessReferenz, variables);
  }
}
