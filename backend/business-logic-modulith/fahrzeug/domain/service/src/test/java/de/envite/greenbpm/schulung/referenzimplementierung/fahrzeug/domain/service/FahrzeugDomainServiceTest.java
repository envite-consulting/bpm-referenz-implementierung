package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.out.FahrzeugStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FahrzeugDomainServiceTest {

    private final FahrzeugStore fahrzeugStoreMock = mock(FahrzeugStore.class);

    private FahrzeugDomainService classUnderTest;

    @BeforeEach
    void setUp() {
        classUnderTest = new FahrzeugDomainService(fahrzeugStoreMock);
    }

    @Test
    void should_query_via_use_case() {
        final FahrzeugId fahrzeugId = mock(FahrzeugId.class);
        final Fahrzeug expectedResult = mock(Fahrzeug.class);
        when(fahrzeugStoreMock.find(fahrzeugId)).thenReturn(expectedResult);

        Fahrzeug result = classUnderTest.query(fahrzeugId);

        assertThat(result).isEqualTo(expectedResult);
    }
}