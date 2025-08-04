package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller;

import static io.github.domainprimitives.validation.Constraints.hasMinLength;
import static io.github.domainprimitives.validation.Constraints.isNotNull;

import io.github.domainprimitives.type.ValueObject;

public class Abteilung extends ValueObject<String> {
  public Abteilung(String value) {
    super(value, hasMinLength(2));
  }
}
