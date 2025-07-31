package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity;

import lombok.Data;

@Data
// TODO: Data JDBC
//@Entity
public class FahrzeugEntity {
  String hersteller;
  String modell;
  Integer jahr;
//  @Id
//  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
}
