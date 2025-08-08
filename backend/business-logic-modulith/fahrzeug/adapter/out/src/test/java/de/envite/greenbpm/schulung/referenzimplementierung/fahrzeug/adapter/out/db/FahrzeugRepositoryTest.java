package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class FahrzeugRepositoryTest {

  private final FahrzeugJdbcRepository fahrzeugJdbcRepositoryMock =
      mock(FahrzeugJdbcRepository.class);
  private final FahrzeugDbMapper fahrzeugDbMapperMock = mock(FahrzeugDbMapper.class);

  private FahrzeugRepository classUnderTest;

  @BeforeEach
  void setUp() {
    classUnderTest = new FahrzeugRepository(fahrzeugJdbcRepositoryMock, fahrzeugDbMapperMock);
  }

  @Nested
  class Query {

    @Test
    void should_return_fahrzeug_when_found() throws FahrzeugNotFoundException {

      FahrzeugId fahrzeugId = new FahrzeugId("77adea5c-7a8e-4668-922e-195ea673ba87");

      FahrzeugEntity persistedFahrzeug = mock(FahrzeugEntity.class);
      Fahrzeug returnedFahrzeug = mock(Fahrzeug.class);

      when(fahrzeugJdbcRepositoryMock.findById(fahrzeugId.getValue()))
          .thenReturn(Optional.of(persistedFahrzeug));
      when(fahrzeugDbMapperMock.toDomain(persistedFahrzeug)).thenReturn(returnedFahrzeug);

      Fahrzeug result = classUnderTest.query(fahrzeugId);

      assertThat(result).isEqualTo(returnedFahrzeug);
    }

    @Test
    void should_throw_exception_when_not_found() {

      FahrzeugId fahrzeugId = new FahrzeugId("305bb78f-9079-4b28-b21f-4cd99d3262e2");

      when(fahrzeugJdbcRepositoryMock.findById(fahrzeugId.getValue())).thenReturn(Optional.empty());

      assertThatThrownBy(() -> classUnderTest.query(fahrzeugId))
          .isInstanceOf(FahrzeugNotFoundException.class)
          .hasMessageContaining(fahrzeugId.getValue());

      verifyNoInteractions(fahrzeugDbMapperMock);
    }

    @Test
    void should_return_all_fahrzeug() {

      FahrzeugEntity persistedFahrzeug1 = mock(FahrzeugEntity.class);
      FahrzeugEntity persistedFahrzeug2 = mock(FahrzeugEntity.class);
      Fahrzeug returnedFahrzeug1 = mock(Fahrzeug.class);
      Fahrzeug returnedFahrzeug2 = mock(Fahrzeug.class);

      when(fahrzeugJdbcRepositoryMock.findAll())
          .thenReturn(List.of(persistedFahrzeug1, persistedFahrzeug2));
      when(fahrzeugDbMapperMock.toDomain(persistedFahrzeug1)).thenReturn(returnedFahrzeug1);
      when(fahrzeugDbMapperMock.toDomain(persistedFahrzeug2)).thenReturn(returnedFahrzeug2);

      List<Fahrzeug> result = classUnderTest.queryAll();

      assertThat(result).containsOnly(returnedFahrzeug1, returnedFahrzeug2);
    }

    @Test
    void should_return_empty_list_when_no_fahrzeug_exist() {

      when(fahrzeugJdbcRepositoryMock.findAll()).thenReturn(List.of());

      List<Fahrzeug> result = classUnderTest.queryAll();

      assertThat(result).containsOnly();

      verifyNoInteractions(fahrzeugDbMapperMock);
    }
  }
}
