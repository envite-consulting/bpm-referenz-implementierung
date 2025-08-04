package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model;

import static io.github.domainprimitives.validation.Constraints.isUUID;

import io.github.domainprimitives.type.ValueObject;

public class Antragstellerreferenz extends ValueObject<String> {
  public Antragstellerreferenz(String value) {
    super(value, isUUID());
  }
}
