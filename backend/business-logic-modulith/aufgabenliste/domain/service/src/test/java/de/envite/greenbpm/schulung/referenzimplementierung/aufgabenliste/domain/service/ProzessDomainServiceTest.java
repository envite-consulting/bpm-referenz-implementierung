package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.service;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.ProzessCommand;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProzessDomainServiceTest {

  private final ProzessCommand prozessCommand = mock(ProzessCommand.class);

  private ProzessDomainService classUnderTest;

  @BeforeEach
  void setUp() {
    classUnderTest = new ProzessDomainService(prozessCommand);
  }

  @Test
  void should_start_prozess_with_variables() {
    String prozessReferenz = "id123";
    String fachlicherSchluessel = "key123";
    Map<String, Object> variables = Map.of("var1", "value1");

    classUnderTest.starten(prozessReferenz, fachlicherSchluessel, variables);

    verify(prozessCommand).start(prozessReferenz, fachlicherSchluessel, variables);
  }
}
