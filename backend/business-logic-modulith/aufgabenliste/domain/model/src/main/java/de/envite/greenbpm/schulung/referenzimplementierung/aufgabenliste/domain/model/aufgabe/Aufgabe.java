package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe;

import io.github.domainprimitives.object.Aggregate;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Aufgabe extends Aggregate {

  private final String id;
  private final String name;
  private final String bearbeiter;
  private final LocalDateTime erstelldatum;
  private final String formularreferenz;
  private final Integer prioritaet;
  private final String vorgangId;
  private final String arbeitspaket;

  public Aufgabe(
      String id,
      String name,
      String bearbeiter,
      LocalDateTime erstelldatum,
      String formularreferenz,
      Integer prioritaet,
      String vorgangId,
      String arbeitspaket) {
    this.id = id;
    this.name = name;
    this.bearbeiter = bearbeiter;
    this.erstelldatum = erstelldatum;
    this.formularreferenz = formularreferenz;
    this.prioritaet = prioritaet;
    this.vorgangId = vorgangId;
    this.arbeitspaket = arbeitspaket;

    validate();
  }

  @Override
  protected void validate() {
    validateNotNull(id, "Aufgabe ID");
    validateNotNull(name, "Name");
    validateNotNull(erstelldatum, "Erstelldatum");
    validateNotNull(formularreferenz, "Formularreferenz");
    validateNotNull(prioritaet, "Prioritaet");
    validateNotNull(vorgangId, "Vorgang ID");
    evaluateValidations();
  }
}
