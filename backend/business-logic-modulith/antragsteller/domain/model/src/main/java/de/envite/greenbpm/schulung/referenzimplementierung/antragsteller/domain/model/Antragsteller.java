package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model;

import io.github.domainprimitives.object.Aggregate;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Antragsteller extends Aggregate {

  private final AntragstellerId antragstellerId;
  private final Vorname vorname;
  private final Nachname nachname;
  private final Abteilung abteilung;

  public Antragsteller(AntragstellerId antragstellerId, Vorname vorname, Nachname nachname, Abteilung abteilung) {
    this.antragstellerId = antragstellerId;
    this.vorname = vorname;
    this.nachname = nachname;
    this.abteilung = abteilung;
    this.validate();
  }

  @Override
  protected void validate() {
    validateNotNull(antragstellerId, "Antragsteller ID");
    validateNotNull(vorname, "Vorname");
    validateNotNull(nachname, "Nachname");
    validateNotNull(abteilung, "Abteilung");
    evaluateValidations();
  }
}
