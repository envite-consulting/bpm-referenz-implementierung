package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model;

import io.github.domainprimitives.object.Aggregate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Bestellung extends Aggregate {

    private final Antragstellerreferenz antragstellerreferenz;
    private final Fahrzeugreferenz fahrzeugreferenz;
    private final Bestelldatum bestelldatum;
    private final Status status;
    private BestellungId bestellungId;

    public Bestellung(Antragstellerreferenz antragstellerreferenz, Fahrzeugreferenz fahrzeugreferenz, Bestelldatum bestelldatum, Status status) {
        this.antragstellerreferenz = antragstellerreferenz;
        this.fahrzeugreferenz = fahrzeugreferenz;
        this.bestelldatum = bestelldatum;
        this.status = status;
        this.validate();
    }

    public Bestellung(BestellungId bestellungId, Antragstellerreferenz antragstellerreferenz, Fahrzeugreferenz fahrzeugreferenz, Bestelldatum bestelldatum, Status status) {
        this(antragstellerreferenz, fahrzeugreferenz, bestelldatum, status);
        this.bestellungId = bestellungId;

        validateNotNull(bestellungId, "Bestellung ID");
        evaluateValidations();
    }

    @Override
    protected void validate() {

        validateNotNull(antragstellerreferenz, "Antragsteller ID");
        validateNotNull(fahrzeugreferenz, "Fahrzeugreferenz");
        validateNotNull(bestelldatum, "Bestelldatum");
        validateNotNull(status, "Status");
        evaluateValidations();
    }
}
