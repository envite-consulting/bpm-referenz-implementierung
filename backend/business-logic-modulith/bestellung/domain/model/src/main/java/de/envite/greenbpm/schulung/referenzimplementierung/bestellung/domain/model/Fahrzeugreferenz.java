package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model;

import static io.github.domainprimitives.validation.Constraints.isUUID;

import io.github.domainprimitives.type.ValueObject;

public class Fahrzeugreferenz extends ValueObject<String> {
  public Fahrzeugreferenz(String value) {
    super(value, isUUID());
  }
}
