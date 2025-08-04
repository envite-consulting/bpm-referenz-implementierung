package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in.BestellungsAbfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in.BestellungsErfassung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.AntragstellerQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.BestellungStore;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.FahrzeugQuery;
import io.github.domainprimitives.validation.InvariantException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BestellungDomainService implements BestellungsAbfrage, BestellungsErfassung {

  private final BestellungStore bestellungStore;
  private final FahrzeugQuery fahrzeugQuery;
  private final AntragstellerQuery antragstellerQuery;

  @Override
  public Bestellung erfassen(Bestellung bestellung) {
    final boolean doesFahrzeugExist =
        fahrzeugQuery.validateExistence(bestellung.getFahrzeugreferenz());
    final boolean doesAntragstellerExist =
        antragstellerQuery.validateExistence(bestellung.getAntragstellerreferenz());
    if (!doesFahrzeugExist) {
      throw new InvariantException(
          "Fahrzeug",
          String.format(
              "Fahrzeug mit der ID %s existiert nicht",
              bestellung.getFahrzeugreferenz().getValue()));
    }
    if (!doesAntragstellerExist) {
      throw new InvariantException(
          "Antragsteller",
          String.format(
              "Antragsteller mit der ID %s existiert nicht",
              bestellung.getAntragstellerreferenz().getValue()));
    }

    return bestellungStore.persist(bestellung);
  }

  @Override
  public Bestellung abfragen(BestellungId bestellungId) {
    return bestellungStore.query(bestellungId);
  }
}
