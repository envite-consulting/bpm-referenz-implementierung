package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.db;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

            SoftAssertions softly = new SoftAssertions();
            softly.assertThat(result).isEqualTo(returnedFahrzeug);
            softly.assertAll();

            verify(fahrzeugJdbcRepositoryMock).findById(fahrzeugId.getValue());
            verify(fahrzeugDbMapperMock).toDomain(persistedFahrzeug);
        }

        @Test
        void should_throw_exception_when_not_found() {

            FahrzeugId fahrzeugId = new FahrzeugId("305bb78f-9079-4b28-b21f-4cd99d3262e2");

            when(fahrzeugJdbcRepositoryMock.findById(fahrzeugId.getValue()))
                    .thenReturn(Optional.empty());

            Assertions.assertThatThrownBy(() -> classUnderTest.query(fahrzeugId))
                    .isInstanceOf(FahrzeugNotFoundException.class)
                    .hasMessageContaining(fahrzeugId.getValue());

            verify(fahrzeugJdbcRepositoryMock).findById(fahrzeugId.getValue());
            verifyNoInteractions(fahrzeugDbMapperMock);
        }
    }

}