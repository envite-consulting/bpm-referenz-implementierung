package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in.BestellungsAbfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in.BestellungsErfassung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.BestellungStore;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.FahrzeugQuery;
import io.github.domainprimitives.validation.InvariantException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BestellungService implements BestellungsAbfrage, BestellungsErfassung {

  private final BestellungStore bestellungStore;
  private final FahrzeugQuery fahrzeugQuery;

  @Override
  public Bestellung erfassen(Bestellung bestellung) {
    final boolean doesFahrzeugExist = fahrzeugQuery.validateExistence(bestellung.getFahrzeugReferenz());
    if (!doesFahrzeugExist) {
      throw new InvariantException(
              "Fahrzeug",
              String.format("Fahrzeug mit der ID %s existiert nicht", bestellung.getFahrzeugReferenz().getValue())
      );
    }
    return bestellungStore.persist(bestellung);
  }

  @Override
  public Bestellung abfragen(BestellungId bestellungId) {
    return bestellungStore.query(bestellungId);
  }
}
