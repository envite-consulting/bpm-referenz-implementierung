package de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug;

import io.github.domainprimitives.object.ComposedValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Fahrzeug extends ComposedValueObject {

  private FahrzeugId fahrzeugId;
  private final Hersteller hersteller;
  private final Modell modell;
  private final Jahr jahr;

  public Fahrzeug(Hersteller hersteller, Modell modell, Jahr jahr) {
    this.hersteller = hersteller;
    this.modell = modell;
    this.jahr = jahr;
    this.validate();
  }

  public Fahrzeug(FahrzeugId fahrzeugId, Hersteller hersteller, Modell modell, Jahr jahr) {
    this(hersteller, modell, jahr);
    this.fahrzeugId = fahrzeugId;
    validateNotNull(fahrzeugId, "Fahrzeug ID");
    evaluateValidations();
  }

  @Override
  protected void validate() {
    validateNotNull(hersteller, "Hersteller");
    validateNotNull(modell, "Modell");
    validateNotNull(jahr, "Jahr");
    evaluateValidations();
  }
}
