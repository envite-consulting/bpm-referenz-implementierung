package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.Aufgabe;
import java.util.List;

public interface Aufgabenabfrage {

  Aufgabe abfragen(String aufgabenId);

  List<Aufgabe> abfragenAlle();
}
