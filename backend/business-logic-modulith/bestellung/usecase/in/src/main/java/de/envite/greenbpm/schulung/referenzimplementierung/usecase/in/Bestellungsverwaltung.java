package de.envite.greenbpm.schulung.referenzimplementierung.usecase.in;

import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;

import java.util.UUID;

public interface Bestellungsverwaltung {

    Bestellung erfassen(Bestellung bestellung);

    Bestellung anzeigen(UUID bestellungId);
}
