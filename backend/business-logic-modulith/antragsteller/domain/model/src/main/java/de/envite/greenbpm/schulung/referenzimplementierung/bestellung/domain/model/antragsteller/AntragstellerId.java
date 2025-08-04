package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller;

import io.github.domainprimitives.type.ValueObject;

import static io.github.domainprimitives.validation.Constraints.isUUID;

public class AntragstellerId extends ValueObject<String> {
    public AntragstellerId(String value) {
        super(value, isUUID());
    }
}
