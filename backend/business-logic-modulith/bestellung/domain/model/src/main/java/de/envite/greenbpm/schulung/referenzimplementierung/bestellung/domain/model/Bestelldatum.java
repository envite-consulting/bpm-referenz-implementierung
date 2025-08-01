package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model;

import io.github.domainprimitives.type.ValueObject;

import java.time.LocalDateTime;

import static io.github.domainprimitives.validation.Constraints.isInPast;

public class Bestelldatum extends ValueObject<LocalDateTime> {
    public Bestelldatum(LocalDateTime value) {
        super(value, isInPast().toString());
    }
}
