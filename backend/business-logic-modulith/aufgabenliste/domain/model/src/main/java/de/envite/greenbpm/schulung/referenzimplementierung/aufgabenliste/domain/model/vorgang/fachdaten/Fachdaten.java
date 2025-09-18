package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten;

import io.github.domainprimitives.object.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Fachdaten extends Entity {

  private final FachdatenAntragsteller antragsteller;
  private final FachdatenFahrzeug fahrzeug;

  public Fachdaten(FachdatenAntragsteller antragsteller, FachdatenFahrzeug fahrzeug) {
    this.antragsteller = antragsteller;
    this.fahrzeug = fahrzeug;

    validate();
  }

  @Override
  protected void validate() {
    validateNotNull(antragsteller, "Antragsteller");
    validateNotNull(fahrzeug, "Fahrzeug");
    evaluateValidations();
  }
}
