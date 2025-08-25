package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out;

import java.util.Map;

public interface ProzessCommand {

  void start(String prozessReferenz, Map<String, Object> variables);
}
