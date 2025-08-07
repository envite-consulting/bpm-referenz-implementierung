package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.AntragstellerId;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.exception.AntragstellerNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.out.AntragstellerStore;
import java.util.List;
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

    return antragstellerJdbcRepository
        .findById(antragstellerId.getValue())
        .map(antragstellerDbMapper::toDomain)
        .orElseThrow(
            () ->
                new AntragstellerNotFoundException(
                    String.format(
                        "Antragsteller mit der ID %s nicht gefunden", antragstellerId.getValue())));
  }

  @Override
  public List<Antragsteller> queryAll() {

    return antragstellerJdbcRepository.findAll().stream()
            .map(antragstellerDbMapper::toDomain).toList();
  }
}
