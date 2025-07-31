package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class BestellungEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "fahrzeug_id", referencedColumnName = "id")
    FahrzeugEntity fahrzeug;

    UUID antragstellerId;
    LocalDateTime bestelldatum;
    String status;
}
