package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model;

import io.github.domainprimitives.object.Aggregate;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Bestellung extends Aggregate {

    private final BestellungId bestellungId;
    private final Antragstellerreferenz antragstellerreferenz;
    private final Fahrzeugreferenz fahrzeugreferenz;
    private final Bestelldatum bestelldatum;
    private final Status status;

    public Bestellung(
            BestellungId bestellungId,
            Antragstellerreferenz antragstellerreferenz,
            Fahrzeugreferenz fahrzeugreferenz,
            Bestelldatum bestelldatum,
            Status status
    ) {
        this.bestellungId = bestellungId;
        this.antragstellerreferenz = antragstellerreferenz;
        this.fahrzeugreferenz = fahrzeugreferenz;
        this.bestelldatum = bestelldatum;
        this.status = status;

        validate();
    }

    public Bestellung(
            Antragstellerreferenz antragstellerreferenz,
            Fahrzeugreferenz fahrzeugreferenz,
            Bestelldatum bestelldatum,
            Status status
    ) {
        this(
                new BestellungId(UUID.randomUUID().toString()),
                antragstellerreferenz,
                fahrzeugreferenz,
                bestelldatum,
                status
                );
    }

    @Override
    protected void validate() {
        validateNotNull(bestellungId, "Bestellung ID");
        validateNotNull(antragstellerreferenz, "Antragstellerreferenz");
        validateNotNull(fahrzeugreferenz, "Fahrzeugreferenz");
        validateNotNull(bestelldatum, "Bestelldatum");
        validateNotNull(status, "Status");
        evaluateValidations();
    }
}
