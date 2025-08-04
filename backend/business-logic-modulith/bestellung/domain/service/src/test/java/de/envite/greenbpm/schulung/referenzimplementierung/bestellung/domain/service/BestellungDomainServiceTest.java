package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Antragstellerreferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Fahrzeugreferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception.BestellungNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.AntragstellerQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.BestellungStore;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.FahrzeugQuery;
import io.github.domainprimitives.validation.InvariantException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BestellungDomainServiceTest {

  private final BestellungStore bestellungStoreMock = mock(BestellungStore.class);
  private final FahrzeugQuery fahrzeugQueryMock = mock(FahrzeugQuery.class);
  private final AntragstellerQuery antragstellerQueryMock = mock(AntragstellerQuery.class);

  private BestellungDomainService classUnderTest;

  @BeforeEach
  void setUp() {
    classUnderTest =
        new BestellungDomainService(bestellungStoreMock, fahrzeugQueryMock, antragstellerQueryMock);
  }

  @Nested
  class Erfassung {

    @Test
    void should_persists_on_erfassen() {
      Fahrzeugreferenz fahrzeugreferenzInput = mock(Fahrzeugreferenz.class);
      Antragstellerreferenz antragstellerreferenzInput = mock(Antragstellerreferenz.class);
      final Bestellung bestellungInput = mock(Bestellung.class);
      when(bestellungInput.getFahrzeugreferenz()).thenReturn(fahrzeugreferenzInput);
      when(bestellungInput.getAntragstellerreferenz()).thenReturn(antragstellerreferenzInput);
      final Bestellung expectedResult = mock(Bestellung.class);
      when(bestellungStoreMock.persist(bestellungInput)).thenReturn(expectedResult);
      when(fahrzeugQueryMock.validateExistence(fahrzeugreferenzInput)).thenReturn(true);
      when(antragstellerQueryMock.validateExistence(antragstellerreferenzInput)).thenReturn(true);

      Bestellung result = classUnderTest.erfassen(bestellungInput);

      assertThat(result).isEqualTo(expectedResult);

      verify(fahrzeugQueryMock).validateExistence(fahrzeugreferenzInput);
      verify(bestellungStoreMock).persist(bestellungInput);
    }

    @Test
    void should_throw_on_erfassen_if_fahzeug_does_not_exists() {
      Fahrzeugreferenz fahrzeugreferenzInput = mock(Fahrzeugreferenz.class);
      Antragstellerreferenz antragstellerreferenzInput = mock(Antragstellerreferenz.class);
      final Bestellung bestellungInput = mock(Bestellung.class);
      when(fahrzeugreferenzInput.getValue()).thenReturn("11");
      when(bestellungInput.getFahrzeugreferenz()).thenReturn(fahrzeugreferenzInput);
      when(bestellungInput.getAntragstellerreferenz()).thenReturn(antragstellerreferenzInput);
      when(fahrzeugQueryMock.validateExistence(fahrzeugreferenzInput)).thenReturn(false);
      when(antragstellerQueryMock.validateExistence(antragstellerreferenzInput)).thenReturn(true);

      assertThatThrownBy(() -> classUnderTest.erfassen(bestellungInput))
          .isInstanceOf(InvariantException.class)
          .hasMessage("Value of Fahrzeug is not valid: Fahrzeug mit der ID 11 existiert nicht.");

      verifyNoInteractions(bestellungStoreMock);
    }

    @Test
    void should_throw_on_erfassen_if_antragsteller_does_not_exists() {
      Fahrzeugreferenz fahrzeugreferenzInput = mock(Fahrzeugreferenz.class);
      Antragstellerreferenz antragstellerreferenzInput = mock(Antragstellerreferenz.class);
      final Bestellung bestellungInput = mock(Bestellung.class);
      when(antragstellerreferenzInput.getValue()).thenReturn("11");
      when(bestellungInput.getFahrzeugreferenz()).thenReturn(fahrzeugreferenzInput);
      when(bestellungInput.getAntragstellerreferenz()).thenReturn(antragstellerreferenzInput);
      when(fahrzeugQueryMock.validateExistence(fahrzeugreferenzInput)).thenReturn(true);
      when(antragstellerQueryMock.validateExistence(antragstellerreferenzInput)).thenReturn(false);

      assertThatThrownBy(() -> classUnderTest.erfassen(bestellungInput))
          .isInstanceOf(InvariantException.class)
          .hasMessage(
              "Value of Antragsteller is not valid: Antragsteller mit der ID 11 existiert nicht.");

      verifyNoInteractions(bestellungStoreMock);
    }
  }

  @Nested
  class Abfragen {

    @Test
    void should_query() {
      final BestellungId bestellungIdInput = mock(BestellungId.class);
      final Bestellung expectedResult = mock(Bestellung.class);
      when(bestellungStoreMock.query(bestellungIdInput)).thenReturn(expectedResult);

      Bestellung result = classUnderTest.abfragen(bestellungIdInput);

      assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void should_not_catch_custom_exception() {
      final BestellungId bestellungIdInput = mock(BestellungId.class);
      final BestellungNotFoundException exception = mock(BestellungNotFoundException.class);
      when(bestellungStoreMock.query(bestellungIdInput)).thenThrow(exception);

      assertThatThrownBy(() -> classUnderTest.abfragen(bestellungIdInput)).isEqualTo(exception);
    }
  }
}
