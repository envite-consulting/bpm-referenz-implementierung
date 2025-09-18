package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.ProzessstartException;
import java.util.Map;

public interface ProzessCommand {

  void start(String processDefinitionId, String businessKey, Map<String, Object> variables)
      throws ProzessstartException;
}
