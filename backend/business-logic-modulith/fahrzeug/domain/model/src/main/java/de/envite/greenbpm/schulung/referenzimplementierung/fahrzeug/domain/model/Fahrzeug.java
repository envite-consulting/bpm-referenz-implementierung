package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model;

import io.github.domainprimitives.object.Aggregate;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Fahrzeug extends Aggregate {

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

  public void setFahrzeugId(FahrzeugId fahrzeugId) {
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
