package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.db;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.AntragstellerId;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.exception.AntragstellerNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
    }

    @Test
    void should_throw_exception_when_not_found() {

      AntragstellerId antragstellerId = new AntragstellerId("f45fd940-afea-4e7b-be57-48e0f5e0071f");

      when(antragstellerJdbcRepositoryMock.findById(antragstellerId.getValue()))
          .thenReturn(Optional.empty());

      assertThatThrownBy(() -> classUnderTest.query(antragstellerId))
          .isInstanceOf(AntragstellerNotFoundException.class)
          .hasMessageContaining(antragstellerId.getValue());

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
    }

    @Test
    void should_return_empty_list_when_no_antragsteller_exist() {

      when(antragstellerJdbcRepositoryMock.findAll()).thenReturn(List.of());

      List<Antragsteller> result = classUnderTest.queryAll();

      assertThat(result).containsOnly();

      verifyNoInteractions(antragstellerDbMapperMock);
    }

    @ParameterizedTest
    @CsvSource({"true, true", "false, false"})
    void should_return_expected_result_when_checking_existence(
        boolean jdbcRepositoryResult, boolean expectedResult) {

      AntragstellerId antragstellerId = new AntragstellerId("884e34b8-5628-48cd-a2a0-d5bc6ca29c55");

      when(antragstellerJdbcRepositoryMock.existsById(antragstellerId.getValue()))
          .thenReturn(jdbcRepositoryResult);

      boolean result = classUnderTest.existsById(antragstellerId);

      assertThat(result).isEqualTo(expectedResult);
    }
  }
}
