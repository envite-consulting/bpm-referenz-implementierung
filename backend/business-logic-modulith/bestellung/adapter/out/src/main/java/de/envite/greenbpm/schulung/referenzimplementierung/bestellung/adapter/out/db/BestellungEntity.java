package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator.UuidEntity;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("BESTELLUNG")
@Data
class BestellungEntity extends UuidEntity {
  private String fahrzeugreferenz;
  private Long antragstellerId;
  private LocalDateTime bestelldatum;
  private String status;
}
