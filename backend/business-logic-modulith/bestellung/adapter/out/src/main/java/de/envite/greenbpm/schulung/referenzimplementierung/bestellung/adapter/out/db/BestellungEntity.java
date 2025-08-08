package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator.UuidEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("BESTELLUNG")
@Data
@EqualsAndHashCode(callSuper = false)
class BestellungEntity extends UuidEntity {
  private String fahrzeugreferenz;
  private String antragstellerreferenz;
  private LocalDateTime bestelldatum;
  private String status;
}
