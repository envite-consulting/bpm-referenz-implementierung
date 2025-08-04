package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller;

import io.github.domainprimitives.type.ValueObject;

import static io.github.domainprimitives.validation.Constraints.hasMinLength;

public class Vorname extends ValueObject<String> {

    public Vorname(String value) {
        super(value, hasMinLength(2));
    }
}
