package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.antragsteller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.AntragstellerId;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.in.Antragstellerabfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Antragstellerreferenz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AntragstellerAdapterTest {

  private Antragstellerabfrage antragstellerabfrageMock;
  private AntragstellerAdapter classUnderTest;

  @BeforeEach
  void setUp() {
    antragstellerabfrageMock = mock(Antragstellerabfrage.class);
    classUnderTest = new AntragstellerAdapter(antragstellerabfrageMock);
  }

  @ParameterizedTest
  @CsvSource({"true, true", "false, false"})
  void should_return_expected_result_when_validating_existence(
      boolean antragstellerabfrageResult, boolean expectedResult) {

    Antragstellerreferenz referenz =
        new Antragstellerreferenz("473c9540-9f24-4e18-a058-d33810060f16");
    when(antragstellerabfrageMock.existiertAntragsteller(
            new AntragstellerId("473c9540-9f24-4e18-a058-d33810060f16")))
        .thenReturn(antragstellerabfrageResult);

    boolean result = classUnderTest.validateExistence(referenz);

    assertThat(result).isEqualTo(expectedResult);
  }
}
