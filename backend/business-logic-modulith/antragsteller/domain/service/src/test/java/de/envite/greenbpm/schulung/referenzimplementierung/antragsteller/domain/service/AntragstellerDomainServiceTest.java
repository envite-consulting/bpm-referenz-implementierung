package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.exception.AntragstellerNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.out.AntragstellerStore;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.AntragstellerId;
import java.util.List;
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
    verify(antragstellerStoreMock).query(antragstellerIdInput);
  }

  @Test
  void should_not_catch_custom_exception_when_query() {
    final AntragstellerId antragstellerIdInput = mock(AntragstellerId.class);
    final AntragstellerNotFoundException exception = mock(AntragstellerNotFoundException.class);
    when(antragstellerStoreMock.query(antragstellerIdInput)).thenThrow(exception);

    assertThatThrownBy(() -> classUnderTest.abfragen(antragstellerIdInput)).isEqualTo(exception);
    verify(antragstellerStoreMock).query(antragstellerIdInput);
  }

  @Test
  void should_query_all() {
    final Antragsteller expectedResult1 = mock(Antragsteller.class);
    final Antragsteller expectedResult2 = mock(Antragsteller.class);
    when(antragstellerStoreMock.queryAll()).thenReturn(List.of(expectedResult1, expectedResult2));

    List<Antragsteller> result = classUnderTest.abfragenAlle();

    assertThat(result).containsExactly(expectedResult1, expectedResult2);
    verify(antragstellerStoreMock).queryAll();
  }

  @Test
  void should_return_empty_list_when_no_antragsteller_exist() {

    when(antragstellerStoreMock.queryAll()).thenReturn(List.of());

    List<Antragsteller> result = classUnderTest.abfragenAlle();

    assertThat(result).isEmpty();
    verify(antragstellerStoreMock).queryAll();
  }
}
