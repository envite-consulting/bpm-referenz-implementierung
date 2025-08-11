package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.AntragstellerId;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.exception.AntragstellerNotFoundException;
import java.util.List;

public interface AntragstellerStore {

  Antragsteller query(AntragstellerId antragstellerId) throws AntragstellerNotFoundException;

  List<Antragsteller> queryAll();

  boolean existsById(AntragstellerId antragstellerId);
}
