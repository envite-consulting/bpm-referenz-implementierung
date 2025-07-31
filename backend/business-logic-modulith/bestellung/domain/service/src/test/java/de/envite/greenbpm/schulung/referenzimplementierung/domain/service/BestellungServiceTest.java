package de.envite.greenbpm.schulung.referenzimplementierung.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.usecase.exception.BestellungNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.usecase.out.BestellungStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BestellungServiceTest {

    private final BestellungStore bestellungStoreMock = mock(BestellungStore.class);

    private BestellungService classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new BestellungService(bestellungStoreMock);
    }

    @Nested
    class Erfassung {

        @Test
        void should_persists_on_erfassen() {
            final Bestellung bestellungMock = mock(Bestellung.class);
            final Bestellung expectedResult = mock(Bestellung.class);
            when(bestellungStoreMock.persist(bestellungMock)).thenReturn(expectedResult);

            Bestellung result = classUnderTest.erfassen(bestellungMock);

            assertThat(result).isEqualTo(expectedResult);
        }
    }

    @Nested
    class Abfragen {

        @Test
        void should_query() {
            final BestellungId bestellungIdMock = mock(BestellungId.class);
            final Bestellung expectedResult = mock(Bestellung.class);
            when(bestellungStoreMock.query(bestellungIdMock)).thenReturn(expectedResult);

            Bestellung result = classUnderTest.abfragen(bestellungIdMock);

            assertThat(result).isEqualTo(expectedResult);
        }

        @Test
        void should_not_catch_custom_exception() {
            final BestellungId bestellungIdMock = mock(BestellungId.class);
            final BestellungNotFoundException exception = mock(BestellungNotFoundException.class);
            when(bestellungStoreMock.query(bestellungIdMock)).thenThrow(exception);

            assertThatThrownBy(() -> classUnderTest.abfragen(bestellungIdMock)).isEqualTo(exception);
        }
    }
}