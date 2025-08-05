package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;

public interface Bestellungsabfrage {

  Bestellung abfragen(BestellungId bestellungId);
}
