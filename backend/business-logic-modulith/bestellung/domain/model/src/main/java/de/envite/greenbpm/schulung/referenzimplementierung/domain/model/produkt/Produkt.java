package de.envite.greenbpm.schulung.referenzimplementierung.domain.model.produkt;

import io.github.domainprimitives.object.ComposedValueObject;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Produkt extends ComposedValueObject {

    private final Hersteller hersteller;
    private final Modell modell;

    public Produkt(Hersteller hersteller, Modell modell) {
        this.hersteller = hersteller;
        this.modell = modell;
        this.validate();
    }

    @Override
    protected void validate() {
        validateNotNull(hersteller, "Hersteller");
        validateNotNull(modell, "Modell");
        evaluateValidations();
    }
}
