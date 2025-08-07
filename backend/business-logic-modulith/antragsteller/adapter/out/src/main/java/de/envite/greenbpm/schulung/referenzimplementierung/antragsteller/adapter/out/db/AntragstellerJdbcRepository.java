package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.db;

import jakarta.annotation.Nonnull;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AntragstellerJdbcRepository extends CrudRepository<AntragstellerEntity, String> {

  @Nonnull
  List<AntragstellerEntity> findAll();
}
