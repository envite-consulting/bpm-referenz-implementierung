package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception.BestellungNotFoundException;

public interface BestellungStore {

  // TODO: Throw Custom Exception?
  Bestellung persist(Bestellung bestellung);

  Bestellung query(BestellungId bestellungId) throws BestellungNotFoundException;
}
