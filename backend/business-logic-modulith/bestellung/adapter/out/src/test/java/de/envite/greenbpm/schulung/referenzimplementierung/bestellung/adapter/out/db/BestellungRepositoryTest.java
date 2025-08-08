package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception.BestellungNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class BestellungRepositoryTest {

  private final BestellungJdbcRepository bestellungJdbcRepositoryMock =
      mock(BestellungJdbcRepository.class);
  private final BestellungDbMapper bestellungDbMapperMock = mock(BestellungDbMapper.class);

  private BestellungRepository classUnderTest;

  @BeforeEach
  void setUp() {
    classUnderTest = new BestellungRepository(bestellungJdbcRepositoryMock, bestellungDbMapperMock);
  }

  @Nested
  class Persist {

    @Test
    void should_persist() {
      Bestellung inputBestellung = mock(Bestellung.class);
      BestellungEntity bestellungToPersist = mock(BestellungEntity.class);
      BestellungEntity persistedBestellung = mock(BestellungEntity.class);
      Bestellung returnedBestellung = mock(Bestellung.class);

      when(bestellungDbMapperMock.toEntity(inputBestellung)).thenReturn(bestellungToPersist);
      when(bestellungJdbcRepositoryMock.save(bestellungToPersist)).thenReturn(persistedBestellung);
      when(bestellungDbMapperMock.toDomain(persistedBestellung)).thenReturn(returnedBestellung);

      Bestellung result = classUnderTest.persist(inputBestellung);

      assertThat(result).isEqualTo(returnedBestellung);
    }
  }

  @Nested
  class Query {

    @Test
    void should_return_bestellung_when_found() throws BestellungNotFoundException {

      BestellungId bestellungId = new BestellungId("91954d9f-f8d6-4008-bf82-44a1c2a672ad");

      BestellungEntity persistedBestellung = mock(BestellungEntity.class);
      Bestellung returnedBestellung = mock(Bestellung.class);

      when(bestellungJdbcRepositoryMock.findById(bestellungId.getValue()))
          .thenReturn(Optional.of(persistedBestellung));
      when(bestellungDbMapperMock.toDomain(persistedBestellung)).thenReturn(returnedBestellung);

      Bestellung result = classUnderTest.query(bestellungId);

      assertThat(result).isEqualTo(returnedBestellung);
    }

    @Test
    void should_throw_exception_when_not_found() {

      BestellungId bestellungId = new BestellungId("9036712d-d6a1-45d8-b0ee-b4485435c5ee");

      when(bestellungJdbcRepositoryMock.findById(bestellungId.getValue()))
          .thenReturn(Optional.empty());

      assertThatThrownBy(() -> classUnderTest.query(bestellungId))
          .isInstanceOf(BestellungNotFoundException.class)
          .hasMessageContaining(bestellungId.getValue());

      verifyNoInteractions(bestellungDbMapperMock);
    }
  }
}
