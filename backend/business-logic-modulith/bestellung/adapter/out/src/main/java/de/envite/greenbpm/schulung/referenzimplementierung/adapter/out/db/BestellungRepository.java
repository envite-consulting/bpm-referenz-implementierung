package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.BestellungEntity;
import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.FahrzeugEntity;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.usecase.exception.BestellungNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.usecase.out.BestellungStore;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BestellungRepository implements BestellungStore {

  private final BestellungJdbcRepository bestellungJdbcRepository;
  private final FahrzeugJdbcRepository fahrzeugJdbcRepository;
  private final BestellungDbMapper bestellungDbMapper;

  public BestellungRepository(
      BestellungJdbcRepository bestellungJdbcRepository,
      FahrzeugJdbcRepository fahrzeugJdbcRepository,
      BestellungDbMapper bestellungDbMapper) {
    this.bestellungJdbcRepository = bestellungJdbcRepository;
    this.fahrzeugJdbcRepository = fahrzeugJdbcRepository;
    this.bestellungDbMapper = bestellungDbMapper;
  }

  @Override
  public Bestellung persist(Bestellung bestellung) {

    BestellungEntity bestellungEntity = bestellungDbMapper.toEntity(bestellung);

    FahrzeugEntity gespeichertesFahrzeug = fahrzeugJdbcRepository.save(bestellungEntity.getFahrzeug());

    BestellungEntity bestellungMitFahrzeug = bestellungEntity.withFahrzeug(gespeichertesFahrzeug);

    return bestellungDbMapper.toDomain(bestellungJdbcRepository.save(bestellungMitFahrzeug));
  }

  @Override
  public Bestellung query(BestellungId bestellungId) throws BestellungNotFoundException {
    return bestellungDbMapper.toDomain(
        bestellungJdbcRepository
            .findById(bestellungId.getValue())
            .orElseThrow(
                () ->
                    new BestellungNotFoundException(
                        "Bestellung mit der ID %s nicht gefunden."
                            .formatted(bestellungId.getValue()))));
  }
}
