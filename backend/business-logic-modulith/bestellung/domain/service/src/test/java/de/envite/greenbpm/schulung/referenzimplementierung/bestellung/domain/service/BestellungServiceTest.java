package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Fahrzeugreferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception.BestellungNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.BestellungStore;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.FahrzeugQuery;
import io.github.domainprimitives.validation.InvariantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class BestellungServiceTest {

    private final BestellungStore bestellungStoreMock = mock(BestellungStore.class);
    private final FahrzeugQuery fahrzeugQueryMock = mock(FahrzeugQuery.class);

    private BestellungService classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new BestellungService(bestellungStoreMock, fahrzeugQueryMock);
    }

    @Nested
    class Erfassung {

        @Test
        void should_persists_on_erfassen() {
            Fahrzeugreferenz fahzeugreferenzMock = mock(Fahrzeugreferenz.class);
            final Bestellung bestellungMock = mock(Bestellung.class);
            when(bestellungMock.getFahrzeugReferenz()).thenReturn(fahzeugreferenzMock);
            final Bestellung expectedResult = mock(Bestellung.class);
            when(bestellungStoreMock.persist(bestellungMock)).thenReturn(expectedResult);
            when(fahrzeugQueryMock.validateExistence(fahzeugreferenzMock)).thenReturn(true);

            Bestellung result = classUnderTest.erfassen(bestellungMock);

            assertThat(result).isEqualTo(expectedResult);
        }

        @Test
        void should_throw_on_erfassen_if_fahzeug_does_not_exists() {
            Fahrzeugreferenz fahzeugreferenzMock = mock(Fahrzeugreferenz.class);
            final Bestellung bestellungMock = mock(Bestellung.class);
            when(fahzeugreferenzMock.getValue()).thenReturn("11");
            when(bestellungMock.getFahrzeugReferenz()).thenReturn(fahzeugreferenzMock);
            when(fahrzeugQueryMock.validateExistence(fahzeugreferenzMock)).thenReturn(false);

            assertThatThrownBy(() -> classUnderTest.erfassen(bestellungMock))
                    .isInstanceOf(InvariantException.class)
                    .hasMessage("Value of Fahrzeug is not valid: Fahrzeug mit der ID 11 existiert nicht.");

            verifyNoInteractions(bestellungStoreMock);
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