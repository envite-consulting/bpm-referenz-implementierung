package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity;

import de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator.UuidEntity;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("BESTELLUNG")
@Data
public class BestellungEntity extends UuidEntity {
  private FahrzeugEntity fahrzeug;
  private Long antragstellerId;
  private LocalDateTime bestelldatum;
  private String status;

  public BestellungEntity withFahrzeug(FahrzeugEntity neuesFahrzeug) {
    this.setFahrzeug(neuesFahrzeug);
    return this;
  }
}
