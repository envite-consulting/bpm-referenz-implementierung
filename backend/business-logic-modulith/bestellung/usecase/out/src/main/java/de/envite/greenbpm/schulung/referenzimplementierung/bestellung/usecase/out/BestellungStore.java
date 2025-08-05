package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception.BestellungNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception.BestellungPersistenceException;

public interface BestellungStore {

  Bestellung persist(Bestellung bestellung) throws BestellungPersistenceException;

  Bestellung query(BestellungId bestellungId) throws BestellungNotFoundException;
}
