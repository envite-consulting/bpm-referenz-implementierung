package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.out.FahrzeugStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    verify(fahrzeugStoreMock).query(fahrzeugIdInput);
  }

  @Test
  void should_not_catch_custom_exception() {
    final FahrzeugId fahrzeugIdInput = mock(FahrzeugId.class);
    final FahrzeugNotFoundException exception = mock(FahrzeugNotFoundException.class);
    when(fahrzeugStoreMock.query(fahrzeugIdInput)).thenThrow(exception);

    assertThatThrownBy(() -> classUnderTest.abfragen(fahrzeugIdInput)).isEqualTo(exception);

    verify(fahrzeugStoreMock).query(fahrzeugIdInput);
  }

  @Test
  void should_query_all() {
    final Fahrzeug expectedResult1 = mock(Fahrzeug.class);
    final Fahrzeug expectedResult2 = mock(Fahrzeug.class);
    when(fahrzeugStoreMock.queryAll()).thenReturn(List.of(expectedResult1, expectedResult2));

    List<Fahrzeug> result = classUnderTest.abfragenAlle();

    assertThat(result).containsExactly(expectedResult1, expectedResult2);
    verify(fahrzeugStoreMock).queryAll();
  }

  @Test
  void should_return_empty_list_when_no_fahrzeug_exist() {

    when(fahrzeugStoreMock.queryAll()).thenReturn(List.of());

    List<Fahrzeug> result = classUnderTest.abfragenAlle();

    assertThat(result).isEmpty();
    verify(fahrzeugStoreMock).queryAll();
  }
}
