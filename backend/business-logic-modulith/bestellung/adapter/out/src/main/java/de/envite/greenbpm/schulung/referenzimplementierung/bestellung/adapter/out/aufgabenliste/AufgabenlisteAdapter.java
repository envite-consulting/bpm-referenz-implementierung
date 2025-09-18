package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.aufgabenliste;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Prozessverwaltung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.AufgabenlisteCommand;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AufgabenlisteAdapter implements AufgabenlisteCommand {

  private final Prozessverwaltung prozessverwaltung;

  @Override
  public void start(
      String prozessReferenz, String fachlicherSchluessel, Map<String, Object> variablen) {
    prozessverwaltung.starten(prozessReferenz, fachlicherSchluessel, variablen);
  }
}
