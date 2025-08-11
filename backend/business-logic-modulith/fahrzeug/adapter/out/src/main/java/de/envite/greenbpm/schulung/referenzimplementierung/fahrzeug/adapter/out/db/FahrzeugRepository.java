package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.out.FahrzeugStore;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class FahrzeugRepository implements FahrzeugStore {

  private final FahrzeugJdbcRepository fahrzeugJdbcRepository;
  private final FahrzeugDbMapper fahrzeugDbMapper;

  @Override
  public Fahrzeug query(FahrzeugId fahrzeugId) throws FahrzeugNotFoundException {

    return fahrzeugJdbcRepository
        .findById(fahrzeugId.getValue())
        .map(fahrzeugDbMapper::toDomain)
        .orElseThrow(
            () ->
                new FahrzeugNotFoundException(
                    String.format("Fahrzeug mit der ID %s nicht gefunden", fahrzeugId.getValue())));
  }

  @Override
  public List<Fahrzeug> queryAll() {

    return fahrzeugJdbcRepository.findAll().stream().map(fahrzeugDbMapper::toDomain).toList();
  }

  @Override
  public boolean existsById(FahrzeugId fahrzeugId) {
    return fahrzeugJdbcRepository.existsById(fahrzeugId.getValue());
  }
}
