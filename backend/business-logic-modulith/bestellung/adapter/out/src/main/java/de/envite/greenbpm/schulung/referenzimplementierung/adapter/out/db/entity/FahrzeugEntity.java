package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class FahrzeugEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    String hersteller;
    String modell;
    Integer jahr;
}
