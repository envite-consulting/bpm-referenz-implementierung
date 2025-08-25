package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.aufgabenliste;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Prozessverwaltung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.AufgabenlisteCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AufgabenlisteAdapter implements AufgabenlisteCommand {

  private final Prozessverwaltung prozessverwaltung;

  @Override
  public void start(String prozessReferenz, Map<String, Object> variablen) {
    prozessverwaltung.starten(prozessReferenz, variablen);
  }
}
