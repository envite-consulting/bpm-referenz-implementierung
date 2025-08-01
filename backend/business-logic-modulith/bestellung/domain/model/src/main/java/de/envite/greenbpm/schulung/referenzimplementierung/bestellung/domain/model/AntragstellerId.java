package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model;

import io.github.domainprimitives.type.ValueObject;

import static io.github.domainprimitives.validation.Constraints.isNotNullLong;

public class AntragstellerId extends ValueObject<Long> {
  public AntragstellerId(Long value) {
    super(value, isNotNullLong());
  }
}
