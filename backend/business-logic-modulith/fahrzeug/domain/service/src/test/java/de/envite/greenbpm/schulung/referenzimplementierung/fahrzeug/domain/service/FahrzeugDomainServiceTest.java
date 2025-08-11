package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.out.FahrzeugStore;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FahrzeugDomainServiceTest {

  private final FahrzeugStore fahrzeugStoreMock = mock(FahrzeugStore.class);

  private FahrzeugDomainService classUnderTest;

  @BeforeEach
  void setUp() {
    classUnderTest = new FahrzeugDomainService(fahrzeugStoreMock);
  }

  @Test
  void should_query() {
    final FahrzeugId fahrzeugIdInput = mock(FahrzeugId.class);
    final Fahrzeug expectedResult = mock(Fahrzeug.class);
    when(fahrzeugStoreMock.query(fahrzeugIdInput)).thenReturn(expectedResult);

    Fahrzeug result = classUnderTest.abfragen(fahrzeugIdInput);

    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void should_not_catch_custom_exception() {
    final FahrzeugId fahrzeugIdInput = mock(FahrzeugId.class);
    final FahrzeugNotFoundException exception = mock(FahrzeugNotFoundException.class);
    when(fahrzeugStoreMock.query(fahrzeugIdInput)).thenThrow(exception);

    assertThatThrownBy(() -> classUnderTest.abfragen(fahrzeugIdInput)).isEqualTo(exception);
  }

  @Test
  void should_query_all() {
    final Fahrzeug expectedResult1 = mock(Fahrzeug.class);
    final Fahrzeug expectedResult2 = mock(Fahrzeug.class);
    when(fahrzeugStoreMock.queryAll()).thenReturn(List.of(expectedResult1, expectedResult2));

    List<Fahrzeug> result = classUnderTest.abfragenAlle();

    assertThat(result).containsExactly(expectedResult1, expectedResult2);
  }

  @Test
  void should_return_empty_list_when_no_fahrzeug_exist() {

    when(fahrzeugStoreMock.queryAll()).thenReturn(List.of());

    List<Fahrzeug> result = classUnderTest.abfragenAlle();

    assertThat(result).isEmpty();
  }

    @ParameterizedTest
    @CsvSource({"true, true", "false, false"})
    void should_return_expected_result_when_checking_existence(
            boolean storeResult, boolean expectedResult) {
        final FahrzeugId fahrzeugIdInput = mock(FahrzeugId.class);
        when(fahrzeugStoreMock.existsById(fahrzeugIdInput)).thenReturn(storeResult);

        boolean result = classUnderTest.existiertFahrzeug(fahrzeugIdInput);

        assertThat(result).isEqualTo(expectedResult);
    }
}
