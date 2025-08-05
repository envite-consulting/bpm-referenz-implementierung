package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.in;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.exception.AntragstellerNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.AntragstellerId;
import java.util.List;

public interface Antragstellerabfrage {
  Antragsteller abfragen(AntragstellerId antragstellerId) throws AntragstellerNotFoundException;

  List<Antragsteller> abfragenAlle();
}
