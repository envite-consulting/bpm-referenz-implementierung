package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model;

import io.github.domainprimitives.type.ValueObject;

import static io.github.domainprimitives.validation.Constraints.hasMinLength;

public class Modell extends ValueObject<String> {

    public Modell(String value) {
        super(value, hasMinLength(2));
    }
}
