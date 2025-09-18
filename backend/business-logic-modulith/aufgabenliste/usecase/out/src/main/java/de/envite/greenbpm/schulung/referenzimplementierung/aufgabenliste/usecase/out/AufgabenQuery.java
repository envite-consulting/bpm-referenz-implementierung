package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.Aufgabe;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeQueryException;
import java.util.List;

public interface AufgabenQuery {

  Aufgabe queryById(String aufgabenId) throws AufgabeNotFoundException, AufgabeQueryException;

  List<Aufgabe> queryAllByVorgang(String vorgangId) throws AufgabeQueryException;
}
