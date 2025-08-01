package de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public abstract class UuidEntity {
    @Id
    private String id;
}
