package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;

public interface Bestellungserfassung {

  Bestellung erfassen(Bestellung bestellung);
}
