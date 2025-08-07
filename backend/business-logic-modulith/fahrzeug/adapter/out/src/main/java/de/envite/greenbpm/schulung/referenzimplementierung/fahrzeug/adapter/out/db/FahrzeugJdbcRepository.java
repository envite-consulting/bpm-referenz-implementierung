package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.db;

import jakarta.annotation.Nonnull;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface FahrzeugJdbcRepository extends CrudRepository<FahrzeugEntity, String> {

  @Nonnull
  List<FahrzeugEntity> findAll();
}
