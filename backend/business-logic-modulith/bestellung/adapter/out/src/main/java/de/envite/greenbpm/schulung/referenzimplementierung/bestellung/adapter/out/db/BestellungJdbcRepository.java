package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BestellungJdbcRepository extends CrudRepository<BestellungEntity, String> {
}
