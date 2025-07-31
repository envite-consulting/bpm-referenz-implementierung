package de.envite.greenbpm.schulung.referenzimplementierung.domain.model;

import io.github.domainprimitives.type.ValueObject;

import static io.github.domainprimitives.validation.Constraints.isNotNullLong;

public class BestellungId extends ValueObject<Long> {

  public BestellungId(Long value) {
    super(value, isNotNullLong());
  }
}
