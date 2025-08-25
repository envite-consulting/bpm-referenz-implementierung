package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.Aufgabe;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Aufgabenabfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Aufgabenverwaltung;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.AufgabenCommand;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.AufgabenQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
class AufgabeDomainService implements Aufgabenverwaltung, Aufgabenabfrage {

  private final AufgabenCommand aufgabenCommand;
  private final AufgabenQuery aufgabenQuery;

  @Override
  public Aufgabe abfragen(String aufgabenId) {
    return aufgabenQuery.queryById(aufgabenId);
  }

  @Override
  public List<Aufgabe> abfragenAlle() {
    return aufgabenQuery.queryAll();
  }

  @Override
  public void abschliessenMitVariablen(String aufgabenId, Map<String, Object> variables) {

    aufgabenCommand.completeWithVariables(aufgabenId, variables);
  }

  @Override
  public void uebernehmen(String aufgabenId, String userId) {
    aufgabenCommand.claim(aufgabenId, userId);
  }

  @Override
  public void abgeben(String aufgabenId) {
    aufgabenCommand.unclaim(aufgabenId);
  }
}
