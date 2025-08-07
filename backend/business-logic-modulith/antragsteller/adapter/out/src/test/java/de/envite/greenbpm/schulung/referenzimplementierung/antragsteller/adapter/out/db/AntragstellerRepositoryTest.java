package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.exception.AntragstellerNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.AntragstellerId;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AntragstellerRepositoryTest {

  private final AntragstellerJdbcRepository antragstellerJdbcRepositoryMock =
      mock(AntragstellerJdbcRepository.class);
  private final AntragstellerDbMapper antragstellerDbMapperMock = mock(AntragstellerDbMapper.class);

  private AntragstellerRepository classUnderTest;

  @BeforeEach
  void setUp() {
    classUnderTest =
        new AntragstellerRepository(antragstellerJdbcRepositoryMock, antragstellerDbMapperMock);
  }

  @Nested
  class Query {

    @Test
    void should_return_antragsteller_when_found() throws AntragstellerNotFoundException {

      AntragstellerId antragstellerId = new AntragstellerId("0c6e17b5-eb12-4c7e-bab0-2e2f418a11e8");

      AntragstellerEntity persistedAntragsteller = mock(AntragstellerEntity.class);
      Antragsteller returnedAntragsteller = mock(Antragsteller.class);

      when(antragstellerJdbcRepositoryMock.findById(antragstellerId.getValue()))
          .thenReturn(Optional.of(persistedAntragsteller));
      when(antragstellerDbMapperMock.toDomain(persistedAntragsteller))
          .thenReturn(returnedAntragsteller);

      Antragsteller result = classUnderTest.query(antragstellerId);

      assertThat(result).isEqualTo(returnedAntragsteller);

      verify(antragstellerJdbcRepositoryMock).findById(antragstellerId.getValue());
      verify(antragstellerDbMapperMock).toDomain(persistedAntragsteller);
    }

    @Test
    void should_throw_exception_when_not_found() {

      AntragstellerId antragstellerId = new AntragstellerId("f45fd940-afea-4e7b-be57-48e0f5e0071f");

      when(antragstellerJdbcRepositoryMock.findById(antragstellerId.getValue()))
          .thenReturn(Optional.empty());

      assertThatThrownBy(() -> classUnderTest.query(antragstellerId))
          .isInstanceOf(AntragstellerNotFoundException.class)
          .hasMessageContaining(antragstellerId.getValue());

      verify(antragstellerJdbcRepositoryMock).findById(antragstellerId.getValue());
      verifyNoInteractions(antragstellerDbMapperMock);
    }

    @Test
    void should_return_all_antragsteller() {

      AntragstellerEntity persistedAntragsteller1 = mock(AntragstellerEntity.class);
      AntragstellerEntity persistedAntragsteller2 = mock(AntragstellerEntity.class);
      Antragsteller returnedAntragsteller1 = mock(Antragsteller.class);
      Antragsteller returnedAntragsteller2 = mock(Antragsteller.class);

      when(antragstellerJdbcRepositoryMock.findAll())
          .thenReturn(List.of(persistedAntragsteller1, persistedAntragsteller2));
      when(antragstellerDbMapperMock.toDomain(persistedAntragsteller1))
          .thenReturn(returnedAntragsteller1);
      when(antragstellerDbMapperMock.toDomain(persistedAntragsteller2))
          .thenReturn(returnedAntragsteller2);

      List<Antragsteller> result = classUnderTest.queryAll();

      assertThat(result).containsOnly(returnedAntragsteller1, returnedAntragsteller2);

      verify(antragstellerJdbcRepositoryMock).findAll();
      verify(antragstellerDbMapperMock).toDomain(persistedAntragsteller1);
      verify(antragstellerDbMapperMock).toDomain(persistedAntragsteller2);
    }

    @Test
    void should_return_empty_list_when_no_antragsteller_exist() {

      when(antragstellerJdbcRepositoryMock.findAll()).thenReturn(List.of());

      List<Antragsteller> result = classUnderTest.queryAll();

      assertThat(result).containsOnly();

      verify(antragstellerJdbcRepositoryMock).findAll();
      verifyNoInteractions(antragstellerDbMapperMock);
    }
  }
}
