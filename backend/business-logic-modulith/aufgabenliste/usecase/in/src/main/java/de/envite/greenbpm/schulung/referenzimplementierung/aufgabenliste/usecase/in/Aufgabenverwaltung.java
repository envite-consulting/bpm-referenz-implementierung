package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in;

import java.util.Map;

public interface Aufgabenverwaltung {

  void abschliessenMitVariablen(String aufgabenId, Map<String, Object> variables);

  void uebernehmen(String aufgabenId, String userId);

  void abgeben(String aufgabenId);
}
