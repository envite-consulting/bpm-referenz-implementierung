package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.fahrzeug;


import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Fahrzeugreferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.in.FahrzeugQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FarzeugQueryClientTest {

    private final FahrzeugQuery fahrzeugQueryMock = mock(FahrzeugQuery.class);

    private FarzeugQueryClient classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new FarzeugQueryClient(fahrzeugQueryMock);
    }

    @Test
    void should_catch_and_return_true() {
        final Fahrzeugreferenz fahrzeugreferenz = new Fahrzeugreferenz(UUID.randomUUID().toString());
        when(fahrzeugQueryMock.query(new FahrzeugId(fahrzeugreferenz.getValue()))).thenReturn(mock(Fahrzeug.class));

        boolean result = classUnderTest.validateExistence(fahrzeugreferenz);

        assertThat(result).isTrue();
    }

    @Test
    void should_catch_and_return_false() {
        final Fahrzeugreferenz fahrzeugreferenz = new Fahrzeugreferenz(UUID.randomUUID().toString());
        when(fahrzeugQueryMock.query(new FahrzeugId(fahrzeugreferenz.getValue()))).thenThrow(mock(FahrzeugNotFoundException.class));

        boolean result = classUnderTest.validateExistence(fahrzeugreferenz);

        assertThat(result).isFalse();
    }
}