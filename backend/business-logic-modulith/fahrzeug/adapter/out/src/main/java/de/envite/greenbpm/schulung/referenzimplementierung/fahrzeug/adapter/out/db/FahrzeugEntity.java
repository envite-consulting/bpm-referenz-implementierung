package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.db;


import de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator.UuidEntity;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

@Table("FAHRZEUG")
@Data
public class FahrzeugEntity extends UuidEntity {
    private String hersteller;
    private String modell;
    private Integer jahr;
}
