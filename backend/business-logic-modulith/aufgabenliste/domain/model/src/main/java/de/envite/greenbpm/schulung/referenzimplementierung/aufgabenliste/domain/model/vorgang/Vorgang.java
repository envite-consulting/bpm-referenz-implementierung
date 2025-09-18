package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.Fachdaten;
import io.github.domainprimitives.object.Aggregate;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Vorgang extends Aggregate {

  private final String id;
  private final String fachlicherSchluessel;
  private Fachdaten fachdaten;

  public Vorgang(String id, String fachlicherSchluessel) {
    this.id = id;
    this.fachlicherSchluessel = fachlicherSchluessel;
    validate();
  }

  public void fachdatenErgaenzen(Fachdaten fachdaten) {
    validateNotNull(fachdaten, "Fachdaten");
    this.fachdaten = fachdaten;
    evaluateValidations();
  }

  @Override
  protected void validate() {
    validateNotNull(id, "Aufgabe ID");
    validateNotNull(fachlicherSchluessel, "Fachlicher Schluessel");
    evaluateValidations();
  }
}
