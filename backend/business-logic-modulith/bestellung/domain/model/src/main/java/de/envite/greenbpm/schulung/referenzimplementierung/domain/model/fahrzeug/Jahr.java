package de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug;

import io.github.domainprimitives.type.ValueObject;

import java.time.Year;

import static io.github.domainprimitives.validation.Constraints.isLessThanOrEqual;

public class Jahr extends ValueObject<Integer> {
  public Jahr(Integer value) {
    super(value, isLessThanOrEqual(Year.now().getValue()));
  }
}
