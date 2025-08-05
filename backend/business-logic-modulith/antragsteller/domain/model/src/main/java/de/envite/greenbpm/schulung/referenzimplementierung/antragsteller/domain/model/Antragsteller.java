package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model;

import io.github.domainprimitives.object.Aggregate;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Antragsteller extends Aggregate {

  private AntragstellerId antragstellerId;
  private final Vorname vorname;
  private final Nachname nachname;
  private final Abteilung abteilung;

  public Antragsteller(Vorname vorname, Nachname nachname, Abteilung abteilung) {
    this.vorname = vorname;
    this.nachname = nachname;
    this.abteilung = abteilung;
    this.validate();
  }

  public void setAntragstellerId(AntragstellerId antragstellerId) {
    this.antragstellerId = antragstellerId;
    validateNotNull(antragstellerId, "Antragsteller ID");
    evaluateValidations();
  }

  @Override
  protected void validate() {
    validateNotNull(vorname, "Vorname");
    validateNotNull(nachname, "Nachname");
    validateNotNull(abteilung, "Abteilung");
    evaluateValidations();
  }
}
