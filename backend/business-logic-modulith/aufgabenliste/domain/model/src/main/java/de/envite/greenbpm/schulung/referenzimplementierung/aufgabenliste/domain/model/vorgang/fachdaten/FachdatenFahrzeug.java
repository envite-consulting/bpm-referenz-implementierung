package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten;

import io.github.domainprimitives.object.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class FachdatenFahrzeug extends Entity {

  private final String hersteller;
  private final String modell;

  public FachdatenFahrzeug(String hersteller, String modell) {
    this.hersteller = hersteller;
    this.modell = modell;

    validate();
  }

  @Override
  protected void validate() {
    validateNotNull(hersteller, "Fahrzeug Hersteller");
    validateNotNull(modell, "Fahrzeug Modell");
    evaluateValidations();
  }
}
