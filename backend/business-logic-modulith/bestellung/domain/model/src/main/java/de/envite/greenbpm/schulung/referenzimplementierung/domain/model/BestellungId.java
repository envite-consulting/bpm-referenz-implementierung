package de.envite.greenbpm.schulung.referenzimplementierung.domain.model;

import io.github.domainprimitives.type.ValueObject;

import static io.github.domainprimitives.validation.Constraints.isUUID;

public class BestellungId extends ValueObject<String> {

  public BestellungId(String value) {
    super(value, isUUID());
  }
}
