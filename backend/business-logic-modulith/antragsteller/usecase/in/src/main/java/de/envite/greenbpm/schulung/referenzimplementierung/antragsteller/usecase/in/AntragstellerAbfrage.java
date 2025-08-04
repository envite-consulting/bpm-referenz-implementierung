package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.in;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.exception.AntragstellerNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller.AntragstellerId;

public interface AntragstellerAbfrage {
  Antragsteller abfragen(AntragstellerId antragstellerId) throws AntragstellerNotFoundException;
}
