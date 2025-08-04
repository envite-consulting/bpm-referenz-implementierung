package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception.BestellungNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.BestellungStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class BestellungRepository implements BestellungStore {

  private final BestellungJdbcRepository bestellungJdbcRepository;
  private final BestellungDbMapper bestellungDbMapper;

  @Override
  @Transactional
  public Bestellung persist(Bestellung bestellung) {

    BestellungEntity bestellungEntity = bestellungDbMapper.toEntity(bestellung);
    return bestellungDbMapper.toDomain(bestellungJdbcRepository.save(bestellungEntity));
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
