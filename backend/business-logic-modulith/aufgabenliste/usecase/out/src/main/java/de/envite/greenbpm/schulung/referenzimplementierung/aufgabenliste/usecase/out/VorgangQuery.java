package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.Vorgang;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.VorgangNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.VorgangQueryException;
import java.util.List;

public interface VorgangQuery {

  Vorgang queryById(String vorgangId) throws VorgangNotFoundException, VorgangQueryException;

  List<Vorgang> queryAll() throws VorgangQueryException;
}
