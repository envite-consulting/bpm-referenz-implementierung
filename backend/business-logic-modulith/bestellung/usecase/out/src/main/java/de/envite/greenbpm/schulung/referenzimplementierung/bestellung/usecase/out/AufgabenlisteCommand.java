package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out;

import java.util.Map;

public interface AufgabenlisteCommand {

  void start(String prozessReferenz, String businessKey, Map<String, Object> variablen);
}
