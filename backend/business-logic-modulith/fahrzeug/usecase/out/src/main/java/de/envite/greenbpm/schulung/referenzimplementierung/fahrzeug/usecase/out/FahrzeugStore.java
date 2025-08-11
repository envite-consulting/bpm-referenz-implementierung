package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;
import java.util.List;

public interface FahrzeugStore {

  Fahrzeug query(FahrzeugId fahrzeugId) throws FahrzeugNotFoundException;

  List<Fahrzeug> queryAll();

  boolean existsById(FahrzeugId fahrzeugId);
}
