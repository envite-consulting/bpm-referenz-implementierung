package de.envite.greenbpm.schulung.referenzimplementierung.domain.model;

import io.github.domainprimitives.type.ValueObject;

import java.util.UUID;

public class AntragstellerId extends ValueObject<UUID> {
    public AntragstellerId(UUID value) {
        super(value);
    }
}
