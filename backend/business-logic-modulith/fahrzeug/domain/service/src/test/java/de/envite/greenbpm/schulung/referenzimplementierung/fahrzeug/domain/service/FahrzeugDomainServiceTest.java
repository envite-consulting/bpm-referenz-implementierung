package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.out.FahrzeugStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
