package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in;

import java.util.Map;

public interface Prozessverwaltung {

  void starten(String prozessReferenz, String fachlicherSchluessel, Map<String, Object> variablen);
}
