package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Prozessverwaltung;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.ProzessCommand;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// TODO: Prozess-Logik in ein eigenes Modul auslagern
class ProzessDomainService implements Prozessverwaltung {

  private final ProzessCommand prozessCommand;

  @Override
  public void starten(String prozessReferenz, String fachlicherSchluessel, Map<String, Object> variablen) {

    prozessCommand.start(prozessReferenz, fachlicherSchluessel, variablen);
  }
}
