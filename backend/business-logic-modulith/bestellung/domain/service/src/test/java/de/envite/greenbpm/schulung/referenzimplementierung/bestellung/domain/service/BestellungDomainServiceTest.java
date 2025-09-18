package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.service;

import static de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.prozessmodell.ProzessReferenzen.BESTELLUNG_PROZESS_REFERENZ;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Antragstellerreferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Fahrzeugreferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception.BestellungNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception.BestellungPersistenceException;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.AntragstellerQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.AufgabenlisteCommand;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.BestellungStore;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.FahrzeugQuery;
import io.github.domainprimitives.validation.InvariantException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BestellungDomainServiceTest {

  private final BestellungStore bestellungStoreMock = mock(BestellungStore.class);
  private final FahrzeugQuery fahrzeugQueryMock = mock(FahrzeugQuery.class);
  private final AntragstellerQuery antragstellerQueryMock = mock(AntragstellerQuery.class);
  private final AufgabenlisteCommand aufgabenlisteCommandMock = mock(AufgabenlisteCommand.class);

  private BestellungDomainService classUnderTest;

  @BeforeEach
  void setUp() {
    classUnderTest =
        new BestellungDomainService(
            bestellungStoreMock,
            fahrzeugQueryMock,
            antragstellerQueryMock,
            aufgabenlisteCommandMock);
  }

  @Nested
  class Erfassung {

    @Test
    void should_persist_and_start_process_on_erfassen() {
      BestellungId bestellungId = new BestellungId("50738e2f-2f83-4230-a69c-76ec5c53ec5e");

      Fahrzeugreferenz fahrzeugreferenzInput = mock(Fahrzeugreferenz.class);
      Antragstellerreferenz antragstellerreferenzInput = mock(Antragstellerreferenz.class);
      final Bestellung bestellungInput = mock(Bestellung.class);
      when(bestellungInput.getFahrzeugreferenz()).thenReturn(fahrzeugreferenzInput);
      when(bestellungInput.getAntragstellerreferenz()).thenReturn(antragstellerreferenzInput);
      final Bestellung expectedResult = mock(Bestellung.class);
      when(expectedResult.getBestellungId()).thenReturn(bestellungId);
      when(bestellungStoreMock.persist(bestellungInput)).thenReturn(expectedResult);
      when(fahrzeugQueryMock.validateExistence(fahrzeugreferenzInput)).thenReturn(true);
      when(antragstellerQueryMock.validateExistence(antragstellerreferenzInput)).thenReturn(true);

      Bestellung result = classUnderTest.erfassen(bestellungInput);

      assertThat(result).isEqualTo(expectedResult);

      verify(aufgabenlisteCommandMock)
          .start(BESTELLUNG_PROZESS_REFERENZ, bestellungId.getValue(), Map.of());
    }

    @Test
    void should_throw_on_erfassen_if_fahrzeug_does_not_exist() {
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
          .hasMessageContaining("Value(s) of Bestellung is not valid")
          .hasMessageContaining("Fahrzeug mit der ID 11 existiert nicht");

      verifyNoInteractions(bestellungStoreMock);
    }

    @Test
    void should_throw_on_erfassen_if_antragsteller_does_not_exist() {
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
          .hasMessageContaining("Value(s) of Bestellung is not valid")
          .hasMessageContaining("Antragsteller mit der ID 11 existiert nicht");

      verifyNoInteractions(bestellungStoreMock);
    }

    @Test
    void should_throw_on_erfassen_with_multiple_errors() {
      Fahrzeugreferenz fahrzeugreferenzInput = mock(Fahrzeugreferenz.class);
      Antragstellerreferenz antragstellerreferenzInput = mock(Antragstellerreferenz.class);
      final Bestellung bestellungInput = mock(Bestellung.class);
      when(fahrzeugreferenzInput.getValue()).thenReturn("11");
      when(antragstellerreferenzInput.getValue()).thenReturn("22");
      when(bestellungInput.getFahrzeugreferenz()).thenReturn(fahrzeugreferenzInput);
      when(bestellungInput.getAntragstellerreferenz()).thenReturn(antragstellerreferenzInput);
      when(fahrzeugQueryMock.validateExistence(fahrzeugreferenzInput)).thenReturn(false);
      when(antragstellerQueryMock.validateExistence(antragstellerreferenzInput)).thenReturn(false);

      assertThatThrownBy(() -> classUnderTest.erfassen(bestellungInput))
          .isInstanceOf(InvariantException.class)
          .hasMessageContaining("Value(s) of Bestellung is not valid")
          .hasMessageContaining("Fahrzeug mit der ID 11 existiert nicht")
          .hasMessageContaining("Antragsteller mit der ID 22 existiert nicht");

      verifyNoInteractions(bestellungStoreMock);
    }

    @Test
    void should_not_catch_custom_exception() {
      final Bestellung bestellungInput = mock(Bestellung.class);
      final BestellungPersistenceException exception = mock(BestellungPersistenceException.class);
      when(bestellungStoreMock.persist(bestellungInput)).thenThrow(exception);
      when(fahrzeugQueryMock.validateExistence(any())).thenReturn(true);
      when(antragstellerQueryMock.validateExistence(any())).thenReturn(true);

      assertThatThrownBy(() -> classUnderTest.erfassen(bestellungInput)).isEqualTo(exception);
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
