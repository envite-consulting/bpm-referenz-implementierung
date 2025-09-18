package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.Vorgang;
import java.util.List;

public interface Vorgangabfrage {

  Vorgang abfragen(String vorgangId);

  List<Vorgang> abfragenAlle();
}
