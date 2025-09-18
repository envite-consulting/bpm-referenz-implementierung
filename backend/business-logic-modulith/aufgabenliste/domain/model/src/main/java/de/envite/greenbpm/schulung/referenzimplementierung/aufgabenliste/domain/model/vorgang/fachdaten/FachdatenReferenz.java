package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten;

import io.github.domainprimitives.object.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class FachdatenReferenz extends Entity {

  private final String antragstellerreferenz;
  private final String fahrzeugreferenz;

  public FachdatenReferenz(String antragstellerreferenz, String fahrzeugreferenz) {
    this.antragstellerreferenz = antragstellerreferenz;
    this.fahrzeugreferenz = fahrzeugreferenz;

    validate();
  }

  @Override
  protected void validate() {
    validateNotNull(antragstellerreferenz, "Antragstellerreferenz");
    validateNotNull(fahrzeugreferenz, "Fahrzeugreferenz");
    evaluateValidations();
  }
}
