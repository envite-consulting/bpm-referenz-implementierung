package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.fahrzeug;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Fahrzeugreferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.in.Fahrzeugabfrage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FahrzeugAdapterTest {

  private Fahrzeugabfrage fahrzeugabfrageMock;
  private FahrzeugAdapter classUnderTest;

  @BeforeEach
  void setUp() {
    fahrzeugabfrageMock = mock(Fahrzeugabfrage.class);
    classUnderTest = new FahrzeugAdapter(fahrzeugabfrageMock);
  }

  @ParameterizedTest
  @CsvSource({"true, true", "false, false"})
  void should_return_expected_result_when_validating_existence(
      boolean fahrzeugabfrageResult, boolean expectedResult) {

    Fahrzeugreferenz referenz = new Fahrzeugreferenz("1997b66e-4190-41c0-ba4a-3b3194f0c7fa");
    when(fahrzeugabfrageMock.existiertFahrzeug(
            new FahrzeugId("1997b66e-4190-41c0-ba4a-3b3194f0c7fa")))
        .thenReturn(fahrzeugabfrageResult);

    boolean result = classUnderTest.validateExistence(referenz);

    assertThat(result).isEqualTo(expectedResult);
  }
}
