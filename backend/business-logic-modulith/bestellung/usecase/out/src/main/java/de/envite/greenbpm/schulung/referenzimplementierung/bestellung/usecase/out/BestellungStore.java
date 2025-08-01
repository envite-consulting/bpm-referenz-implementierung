package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception.BestellungNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.BestellungId;

public interface BestellungStore {

  // TODO: Throw Custom Exception?
  Bestellung persist(Bestellung bestellung);

  Bestellung query(BestellungId bestellungId) throws BestellungNotFoundException;
}
