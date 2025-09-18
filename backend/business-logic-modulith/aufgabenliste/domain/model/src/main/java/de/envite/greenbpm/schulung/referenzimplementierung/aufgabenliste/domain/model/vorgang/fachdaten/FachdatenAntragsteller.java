package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten;

import io.github.domainprimitives.object.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class FachdatenAntragsteller extends Entity {

  private final String vorname;
  private final String nachname;

  public FachdatenAntragsteller(String vorname, String nachname) {
    this.vorname = vorname;
    this.nachname = nachname;

    validate();
  }

  @Override
  protected void validate() {
    validateNotNull(vorname, "Vorname");
    validateNotNull(nachname, "Nachname");
    evaluateValidations();
  }
}
