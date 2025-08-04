package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.in;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;

public interface FahrzeugAbfrage {
    Fahrzeug abfragen(FahrzeugId fahrzeugId) throws FahrzeugNotFoundException;
}
