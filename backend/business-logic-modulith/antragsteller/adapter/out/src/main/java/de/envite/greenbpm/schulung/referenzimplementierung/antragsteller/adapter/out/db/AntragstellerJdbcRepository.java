package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface AntragstellerJdbcRepository extends CrudRepository<AntragstellerEntity, String> {}
