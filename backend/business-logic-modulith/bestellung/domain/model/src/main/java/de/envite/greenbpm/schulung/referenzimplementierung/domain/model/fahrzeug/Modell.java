package de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug;

import static io.github.domainprimitives.validation.Constraints.hasMinLength;

import io.github.domainprimitives.type.ValueObject;

public class Modell extends ValueObject<String> {

    public Modell(String value) {
        super(value, hasMinLength(2));
    }
}
