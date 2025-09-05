package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model;

import io.github.domainprimitives.type.ValueObject;

import static io.github.domainprimitives.validation.Constraints.hasMinLength;

public class Abteilung extends ValueObject<String> {
  public Abteilung(String value) {
    super(value, hasMinLength(2));
  }
}
