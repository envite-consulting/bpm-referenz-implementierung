package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller.AntragstellerId;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.exception.AntragstellerNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.out.AntragstellerStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AntragstellerDomainServiceTest {

  private final AntragstellerStore antragstellerStoreMock = mock(AntragstellerStore.class);

  private AntragstellerDomainService classUnderTest;

  @BeforeEach
  void setUp() {
    classUnderTest = new AntragstellerDomainService(antragstellerStoreMock);
  }

  @Test
  void should_query() {
    final AntragstellerId antragstellerIdInput = mock(AntragstellerId.class);
    final Antragsteller expectedResult = mock(Antragsteller.class);
    when(antragstellerStoreMock.query(antragstellerIdInput)).thenReturn(expectedResult);

    Antragsteller result = classUnderTest.abfragen(antragstellerIdInput);

    assertThat(result).isEqualTo(expectedResult);
  }

  @Test
  void should_not_catch_custom_exception() {
    final AntragstellerId antragstellerIdInput = mock(AntragstellerId.class);
    final AntragstellerNotFoundException exception = mock(AntragstellerNotFoundException.class);
    when(antragstellerStoreMock.query(antragstellerIdInput)).thenThrow(exception);

    assertThatThrownBy(() -> classUnderTest.abfragen(antragstellerIdInput)).isEqualTo(exception);
  }
}
