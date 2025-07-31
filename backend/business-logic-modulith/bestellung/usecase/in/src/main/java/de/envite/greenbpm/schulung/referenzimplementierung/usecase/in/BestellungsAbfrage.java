package de.envite.greenbpm.schulung.referenzimplementierung.usecase.in;

import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.BestellungId;

public interface BestellungsAbfrage {

  Bestellung abfragen(BestellungId bestellungId);
}
