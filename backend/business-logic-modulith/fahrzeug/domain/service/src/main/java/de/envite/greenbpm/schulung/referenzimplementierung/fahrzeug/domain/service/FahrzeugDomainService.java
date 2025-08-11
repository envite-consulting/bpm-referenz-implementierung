package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.in.Fahrzeugabfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.out.FahrzeugStore;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class FahrzeugDomainService implements Fahrzeugabfrage {

  private final FahrzeugStore fahrzeugStore;

  @Override
  public Fahrzeug abfragen(FahrzeugId fahrzeugId) throws FahrzeugNotFoundException {
    return fahrzeugStore.query(fahrzeugId);
  }

  @Override
  public List<Fahrzeug> abfragenAlle() {
    return fahrzeugStore.queryAll();
  }

  @Override
  public boolean existiertFahrzeug(FahrzeugId fahrzeugId) {
    return fahrzeugStore.existsById(fahrzeugId);
  }
}
