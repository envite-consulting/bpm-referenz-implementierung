package de.envite.greenbpm.schulung.referenzimplementierung.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;

import java.util.UUID;

public interface BestellungStore {

    Bestellung persist(Bestellung bestellung);

    Bestellung query(UUID bestellungsId);
}
