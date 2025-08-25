package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out;

import java.util.Map;

public interface AufgabenCommand {

  void claim(String aufgabenId, String userId);

  void unclaim(String aufgabenId);

  void completeWithVariables(String aufgabenId, Map<String, Object> variables);
}
