package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug;

import io.github.domainprimitives.type.ValueObject;

import static io.github.domainprimitives.validation.Constraints.hasMinLength;

public class Hersteller extends ValueObject<String> {

    public Hersteller(String value) {
        super(value, hasMinLength(2));
    }
}
