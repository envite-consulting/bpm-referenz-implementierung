package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity;

import java.time.LocalDateTime;

public record BestellungEntity(
    Long id,
    FahrzeugEntity fahrzeug,
    Long antragstellerId,
    LocalDateTime bestelldatum,
    String status) {

  public BestellungEntity withFahrzeug(FahrzeugEntity neuesFahrzeug) {
    return new BestellungEntity(
        this.id, neuesFahrzeug, this.antragstellerId, this.bestelldatum, this.status);
  }
}
