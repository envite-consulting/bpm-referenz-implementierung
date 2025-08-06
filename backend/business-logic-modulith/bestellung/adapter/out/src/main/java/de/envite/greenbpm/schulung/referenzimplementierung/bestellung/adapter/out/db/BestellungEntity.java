package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator.UuidEntity;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Table("BESTELLUNG")
@Data
class BestellungEntity extends UuidEntity {
  private String fahrzeugreferenz;
  private String antragstellerreferenz;
  private LocalDateTime bestelldatum;
  private String status;
}
