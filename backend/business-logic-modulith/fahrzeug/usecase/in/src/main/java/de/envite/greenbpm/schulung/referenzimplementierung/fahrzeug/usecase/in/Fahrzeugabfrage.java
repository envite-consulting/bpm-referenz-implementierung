package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.in;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;
import java.util.List;

public interface Fahrzeugabfrage {
  Fahrzeug abfragen(FahrzeugId fahrzeugId) throws FahrzeugNotFoundException;

  List<Fahrzeug> abfragenAlle();
}
