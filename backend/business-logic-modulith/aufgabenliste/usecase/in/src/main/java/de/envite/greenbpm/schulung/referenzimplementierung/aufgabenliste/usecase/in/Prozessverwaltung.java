package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in;

import java.util.Map;

public interface Prozessverwaltung {

  void starten(String prozessReferenz, Map<String, Object> variablen);
}
