package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class BestellungEntity {

  @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "fahrzeug_id", referencedColumnName = "id")
  FahrzeugEntity fahrzeug;
  Long antragstellerId;
  LocalDateTime bestelldatum;
  String status;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
}
