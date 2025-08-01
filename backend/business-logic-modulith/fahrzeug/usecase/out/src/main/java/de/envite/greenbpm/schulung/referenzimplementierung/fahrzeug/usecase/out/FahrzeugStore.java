package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;

public interface FahrzeugStore {

    Fahrzeug find(FahrzeugId fahrzeugId) throws FahrzeugNotFoundException;
}
