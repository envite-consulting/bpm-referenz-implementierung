package de.envite.greenbpm.schulung.referenzimplementierung.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.usecase.exception.BestellungNotFoundException;

public interface BestellungStore {

  // TODO: Throw Custom Exception?
  Bestellung persist(Bestellung bestellung);

  Bestellung query(BestellungId bestellungId) throws BestellungNotFoundException;
}
