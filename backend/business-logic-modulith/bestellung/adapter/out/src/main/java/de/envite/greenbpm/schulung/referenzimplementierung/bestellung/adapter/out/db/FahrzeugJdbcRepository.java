package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.db.entity.FahrzeugEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface FahrzeugJdbcRepository extends CrudRepository<FahrzeugEntity, String> {
}
