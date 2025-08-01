package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model;

import io.github.domainprimitives.type.ValueObject;

import static io.github.domainprimitives.validation.Constraints.isNotNull;

public class Fahrzeugreferenz extends ValueObject<String> {
    public Fahrzeugreferenz(String value) {
        super(value, isNotNull());
    }
}
