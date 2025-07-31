package de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug;

import static io.github.domainprimitives.validation.Constraints.isNotNullLong;

import io.github.domainprimitives.type.ValueObject;

public class FahrzeugId extends ValueObject<Long> {
    public FahrzeugId(Long value) {
        super(value, isNotNullLong());
    }
}
