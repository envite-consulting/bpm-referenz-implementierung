package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller;

import io.github.domainprimitives.type.ValueObject;

import static io.github.domainprimitives.validation.Constraints.hasMinLength;

public class Nachname extends ValueObject<String> {

    public Nachname(String value) {
        super(value, hasMinLength(2));
    }
}
