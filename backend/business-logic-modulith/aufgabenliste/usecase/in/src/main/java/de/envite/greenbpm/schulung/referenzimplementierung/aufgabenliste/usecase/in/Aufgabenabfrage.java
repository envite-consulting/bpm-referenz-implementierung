package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.Aufgabe;
import java.util.List;
import java.util.UUID;

public interface Aufgabenabfrage {

  Aufgabe abfragen(String aufgabenId);

  List<Aufgabe> abfragenAlleZuVorgang(String vorgangId);
}
