package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.BestellungEntity;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.usecase.exception.BestellungNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.usecase.out.BestellungStore;
import org.springframework.stereotype.Component;

@Component
public class BestellungRepository implements BestellungStore {

  private final BestellungCrudRepository bestellungCrudRepository;
  private final BestellungDbMapper bestellungDbMapper;

  public BestellungRepository(
      BestellungCrudRepository bestellungCrudRepository, BestellungDbMapper bestellungDbMapper) {
    this.bestellungCrudRepository = bestellungCrudRepository;
    this.bestellungDbMapper = bestellungDbMapper;
  }

  @Override
  public Bestellung persist(Bestellung bestellung) {
    BestellungEntity entity = bestellungDbMapper.toEntity(bestellung);
    return bestellungDbMapper.toDomain(bestellungCrudRepository.save(entity));
  }

  @Override
  public Bestellung query(BestellungId bestellungId) throws BestellungNotFoundException {
    return bestellungDbMapper.toDomain(
        bestellungCrudRepository
            .findById(bestellungId.getValue())
            .orElseThrow(
                () ->
                    new BestellungNotFoundException(
                        "Bestellung mit der ID %s nicht gefunden."
                            .formatted(bestellungId.getValue()))));
  }
}
