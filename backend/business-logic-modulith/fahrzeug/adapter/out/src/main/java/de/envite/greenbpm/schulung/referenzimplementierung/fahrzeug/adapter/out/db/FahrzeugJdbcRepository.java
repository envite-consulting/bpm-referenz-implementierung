package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface FahrzeugJdbcRepository extends CrudRepository<FahrzeugEntity, String> {
}
