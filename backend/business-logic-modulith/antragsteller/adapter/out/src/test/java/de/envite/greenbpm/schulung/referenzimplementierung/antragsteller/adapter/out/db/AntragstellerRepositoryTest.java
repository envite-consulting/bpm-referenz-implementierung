package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.db;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.exception.AntragstellerNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller.AntragstellerId;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
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

      SoftAssertions softly = new SoftAssertions();
      softly.assertThat(result).isEqualTo(returnedAntragsteller);
      softly.assertAll();

      verify(antragstellerJdbcRepositoryMock).findById(antragstellerId.getValue());
      verify(antragstellerDbMapperMock).toDomain(persistedAntragsteller);
    }

    @Test
    void should_throw_exception_when_not_found() {

      AntragstellerId antragstellerId = new AntragstellerId("f45fd940-afea-4e7b-be57-48e0f5e0071f");

      when(antragstellerJdbcRepositoryMock.findById(antragstellerId.getValue()))
          .thenReturn(Optional.empty());

      Assertions.assertThatThrownBy(() -> classUnderTest.query(antragstellerId))
          .isInstanceOf(AntragstellerNotFoundException.class)
          .hasMessageContaining(antragstellerId.getValue());

      verify(antragstellerJdbcRepositoryMock).findById(antragstellerId.getValue());
      verifyNoInteractions(antragstellerDbMapperMock);
    }
  }
}
