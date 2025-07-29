package de.envite.greenbpm.schulung.referenzimplementierung.domain.model;

import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.produkt.Produkt;
import io.github.domainprimitives.object.Aggregate;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Bestellung extends Aggregate {

    private final AntragstellerId antragstellerId;
    private final Produkt produkt;
    private final Bestelldatum bestelldatum;
    private final Status status;

    public Bestellung(AntragstellerId antragstellerId, Produkt produkt, Bestelldatum bestelldatum, Status status) {
        this.antragstellerId = antragstellerId;
        this.produkt = produkt;
        this.bestelldatum = bestelldatum;
        this.status = status;
        this.validate();
    }

    @Override
    protected void validate() {

        validateNotNull(antragstellerId, "Antragsteller ID");
        validateNotNull(produkt, "Produkt");
        validateNotNull(bestelldatum, "Bestelldatum");
        validateNotNull(status, "Status");
        evaluateValidations();
    }
}
