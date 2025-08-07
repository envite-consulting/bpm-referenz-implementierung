package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.exception.AntragstellerNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.in.Antragstellerabfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.out.AntragstellerStore;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.AntragstellerId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AntragstellerDomainService implements Antragstellerabfrage {

  private final AntragstellerStore antragstellerStore;

  @Override
  public Antragsteller abfragen(AntragstellerId antragstellerId)
      throws AntragstellerNotFoundException {
    return antragstellerStore.query(antragstellerId);
  }

  @Override
  public List<Antragsteller> abfragenAlle() {
    return antragstellerStore.queryAll();
  }
}
