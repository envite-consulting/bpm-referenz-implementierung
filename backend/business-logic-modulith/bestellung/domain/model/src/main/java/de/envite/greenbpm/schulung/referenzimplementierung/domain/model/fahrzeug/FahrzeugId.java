package de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug;

import io.github.domainprimitives.type.ValueObject;

import static io.github.domainprimitives.validation.Constraints.isUUID;

public class FahrzeugId extends ValueObject<String> {
    public FahrzeugId(String value) {
        super(value, isUUID());
    }
}
