package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller.AntragstellerId;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.exception.AntragstellerNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.out.AntragstellerStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class AntragstellerRepository implements AntragstellerStore {

  private final AntragstellerJdbcRepository antragstellerJdbcRepository;
  private final AntragstellerDbMapper antragstellerDbMapper;

  @Override
  public Antragsteller query(AntragstellerId antragstellerId)
      throws AntragstellerNotFoundException {
    AntragstellerEntity entity =
        antragstellerJdbcRepository
            .findById(antragstellerId.getValue())
            .orElseThrow(
                () ->
                    new AntragstellerNotFoundException(
                        String.format(
                            "Antragsteller mit der ID %s nicht gefunden",
                            antragstellerId.getValue())));
    return antragstellerDbMapper.toDomain(entity);
  }
}
