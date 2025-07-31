package de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug;

import static io.github.domainprimitives.validation.Constraints.hasMinLength;

import io.github.domainprimitives.type.ValueObject;

public class Hersteller extends ValueObject<String> {

    public Hersteller(String value) {
        super(value, hasMinLength(2));
    }
}
