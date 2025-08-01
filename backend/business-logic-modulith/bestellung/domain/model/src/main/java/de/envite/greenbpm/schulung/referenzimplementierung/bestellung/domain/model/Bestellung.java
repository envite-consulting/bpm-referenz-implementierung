package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model;

import io.github.domainprimitives.object.Aggregate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Bestellung extends Aggregate {

    private final AntragstellerId antragstellerId;
    private final Fahrzeugreferenz fahrzeugReferenz;
    private final Bestelldatum bestelldatum;
    private final Status status;
    private BestellungId bestellungId;

    public Bestellung(AntragstellerId antragstellerId, Fahrzeugreferenz fahrzeugReferenz, Bestelldatum bestelldatum, Status status) {
        this.antragstellerId = antragstellerId;
        this.fahrzeugReferenz = fahrzeugReferenz;
        this.bestelldatum = bestelldatum;
        this.status = status;
        this.validate();
    }

    public Bestellung(BestellungId bestellungId, AntragstellerId antragstellerId, Fahrzeugreferenz fahrzeugReferenz, Bestelldatum bestelldatum, Status status) {
        this(antragstellerId, fahrzeugReferenz, bestelldatum, status);
        this.bestellungId = bestellungId;

        validateNotNull(bestellungId, "Bestellung ID");
        evaluateValidations();
    }

    @Override
    protected void validate() {

        validateNotNull(antragstellerId, "Antragsteller ID");
        validateNotNull(fahrzeugReferenz, "Fahrzeugreferenz");
        validateNotNull(bestelldatum, "Bestelldatum");
        validateNotNull(status, "Status");
        evaluateValidations();
    }
}
