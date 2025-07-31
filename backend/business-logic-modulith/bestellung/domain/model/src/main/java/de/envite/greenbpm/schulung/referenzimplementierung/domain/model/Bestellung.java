package de.envite.greenbpm.schulung.referenzimplementierung.domain.model;

import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug.Fahrzeug;
import io.github.domainprimitives.object.Aggregate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = false)
@ToString
public class Bestellung extends Aggregate {

    private final AntragstellerId antragstellerId;
    private final Fahrzeug fahrzeug;
    private final Bestelldatum bestelldatum;
    private final Status status;
    private BestellungId bestellungId;

    public Bestellung(AntragstellerId antragstellerId, Fahrzeug fahrzeug, Bestelldatum bestelldatum, Status status) {
        this.antragstellerId = antragstellerId;
        this.fahrzeug = fahrzeug;
        this.bestelldatum = bestelldatum;
        this.status = status;
        this.validate();
    }

    public Bestellung(BestellungId bestellungId, AntragstellerId antragstellerId, Fahrzeug fahrzeug, Bestelldatum bestelldatum, Status status) {
        this(antragstellerId, fahrzeug, bestelldatum, status);
        this.bestellungId = bestellungId;

        validateNotNull(bestellungId, "Bestellung ID");
        evaluateValidations();
    }

    @Override
    protected void validate() {

        validateNotNull(antragstellerId, "Antragsteller ID");
        validateNotNull(fahrzeug, "Produkt");
        validateNotNull(bestelldatum, "Bestelldatum");
        validateNotNull(status, "Status");
        evaluateValidations();
    }
}
