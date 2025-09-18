package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeUpdateException;
import java.util.Map;

public interface AufgabenCommand {

  void claim(String aufgabenId, String userId) throws AufgabeUpdateException;

  void unclaim(String aufgabenId) throws AufgabeUpdateException;

  void completeWithVariables(String aufgabenId, Map<String, Object> variables)
      throws AufgabeUpdateException;
}
