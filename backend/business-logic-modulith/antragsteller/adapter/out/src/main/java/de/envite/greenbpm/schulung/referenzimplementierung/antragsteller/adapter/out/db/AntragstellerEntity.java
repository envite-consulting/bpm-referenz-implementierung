package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator.UuidEntity;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Table("ANTRAGSTELLER")
@Data
class AntragstellerEntity extends UuidEntity {
  private String vorname;
  private String nachname;
  private String abteilung;
}
