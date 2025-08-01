package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in.BestellungsAbfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in.BestellungsErfassung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.BestellungStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BestellungService implements BestellungsAbfrage, BestellungsErfassung {

  private final BestellungStore bestellungStore;

  @Override
  public Bestellung erfassen(Bestellung bestellung) {
    return bestellungStore.persist(bestellung);
  }

  @Override
  public Bestellung abfragen(BestellungId bestellungId) {
    return bestellungStore.query(bestellungId);
  }
}
