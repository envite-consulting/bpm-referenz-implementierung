package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.Aufgabe;
import java.util.List;

public interface AufgabenQuery {

  Aufgabe queryById(String aufgabenId);

  List<Aufgabe> queryAll();
}
