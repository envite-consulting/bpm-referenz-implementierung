package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in.Bestellungsabfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in.Bestellungserfassung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.AntragstellerQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.BestellungStore;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.FahrzeugQuery;
import io.github.domainprimitives.validation.InvariantException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BestellungDomainService implements Bestellungsabfrage, Bestellungserfassung {

  private final BestellungStore bestellungStore;
  private final FahrzeugQuery fahrzeugQuery;
  private final AntragstellerQuery antragstellerQuery;

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

    return bestellungStore.persist(bestellung);
  }

  @Override
  public Bestellung abfragen(BestellungId bestellungId) {
    return bestellungStore.query(bestellungId);
  }
}
