package de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug;

import static io.github.domainprimitives.validation.Constraints.isLessThanOrEqual;

import io.github.domainprimitives.type.ValueObject;
import java.time.Year;

public class Jahr extends ValueObject<Integer> {
  public Jahr(Integer value) {
    super(value, isLessThanOrEqual(Year.now().getValue()));
  }
}
