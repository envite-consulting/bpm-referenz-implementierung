package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class FahrzeugEntity {
  String hersteller;
  String modell;
  Integer jahr;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
}
