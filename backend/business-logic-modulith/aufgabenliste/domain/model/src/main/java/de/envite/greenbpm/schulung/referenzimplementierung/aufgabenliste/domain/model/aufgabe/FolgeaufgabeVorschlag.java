package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe;

import io.github.domainprimitives.object.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class FolgeaufgabeVorschlag extends Entity {

  private final String aufgabeId;
  private final String vorgangId;
  private final String arbeitspaket;

  public FolgeaufgabeVorschlag(String aufgabeId, String vorgangId, String arbeitspaket) {
    this.aufgabeId = aufgabeId;
    this.vorgangId = vorgangId;
    this.arbeitspaket = arbeitspaket;

    validate();
  }

  @Override
  protected void validate() {
    validateNotNull(aufgabeId, "Aufgabe ID");
    validateNotNull(vorgangId, "Vorgang ID");
    evaluateValidations();
  }
}
