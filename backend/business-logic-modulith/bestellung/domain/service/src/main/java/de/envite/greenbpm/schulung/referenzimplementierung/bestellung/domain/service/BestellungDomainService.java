package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.service;

import static de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.prozessmodell.ProzessReferenzen.BESTELLUNG_PROZESS_REFERENZ;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in.Bestellungsabfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in.Bestellungserfassung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.AntragstellerQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.AufgabenlisteCommand;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.BestellungStore;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.FahrzeugQuery;
import io.github.domainprimitives.validation.InvariantException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BestellungDomainService implements Bestellungsabfrage, Bestellungserfassung {

  private final BestellungStore bestellungStore;
  private final FahrzeugQuery fahrzeugQuery;
  private final AntragstellerQuery antragstellerQuery;
  private final AufgabenlisteCommand aufgabenlisteCommand;

  @Override
  public Bestellung erfassen(Bestellung bestellung) {

    List<String> validationErrors = new ArrayList<>();

    if (!fahrzeugQuery.validateExistence(bestellung.getFahrzeugreferenz())) {
      validationErrors.add(
          String.format(
              "Fahrzeug mit der ID %s existiert nicht",
              bestellung.getFahrzeugreferenz().getValue()));
    }
    if (!antragstellerQuery.validateExistence(bestellung.getAntragstellerreferenz())) {
      validationErrors.add(
          String.format(
              "Antragsteller mit der ID %s existiert nicht",
              bestellung.getAntragstellerreferenz().getValue()));
    }

    if (!validationErrors.isEmpty()) {
      throw new InvariantException("Bestellung", validationErrors);
    }

    Bestellung savedBestellung = bestellungStore.persist(bestellung);

    Map<String, Object> variablen = Map.of();
    aufgabenlisteCommand.start(
        BESTELLUNG_PROZESS_REFERENZ, savedBestellung.getBestellungId().getValue(), variablen);

    return savedBestellung;
  }

  @Override
  public Bestellung abfragen(BestellungId bestellungId) {
    return bestellungStore.query(bestellungId);
  }
}
