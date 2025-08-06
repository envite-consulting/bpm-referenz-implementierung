package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model;

import static io.github.domainprimitives.validation.Constraints.isInPast;

import io.github.domainprimitives.type.ValueObject;
import io.github.domainprimitives.validation.Validation;
import java.time.LocalDateTime;
import java.util.function.Consumer;

public class Bestelldatum extends ValueObject<LocalDateTime> {
  public Bestelldatum(LocalDateTime value) {
    super(value, castValidator(isInPast()));
  }

  @SuppressWarnings("unchecked")
  private static Consumer<Validation<LocalDateTime>> castValidator(Consumer<?> validator) {
    return (Consumer<Validation<LocalDateTime>>) validator;
  }
}
