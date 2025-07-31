package de.envite.greenbpm.schulung.referenzimplementierung.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.usecase.in.BestellungsAbfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.usecase.in.BestellungsErfassung;
import de.envite.greenbpm.schulung.referenzimplementierung.usecase.out.BestellungStore;
import org.springframework.stereotype.Service;

@Service
public class BestellungService implements BestellungsAbfrage, BestellungsErfassung {

  private final BestellungStore bestellungStore;

  public BestellungService(BestellungStore bestellungStore) {
    this.bestellungStore = bestellungStore;
  }

  @Override
  public Bestellung erfassen(Bestellung bestellung) {
    return bestellungStore.persist(bestellung);
  }

  @Override
  public Bestellung abfragen(BestellungId bestellungId) {
    return bestellungStore.query(bestellungId);
  }
}
