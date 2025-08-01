package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in;

import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;

public interface BestellungsErfassung {

  Bestellung erfassen(Bestellung bestellung);
}
