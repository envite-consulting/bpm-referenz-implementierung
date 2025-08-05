package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model;

import io.github.domainprimitives.type.ValueObject;

import static io.github.domainprimitives.validation.Constraints.isUUID;

public class AntragstellerId extends ValueObject<String> {
    public AntragstellerId(String value) {
        super(value, isUUID());
    }
}
