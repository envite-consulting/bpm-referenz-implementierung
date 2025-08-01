package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Fahrzeugreferenz;

public interface FahrzeugQuery {

    boolean validateExistence(Fahrzeugreferenz fahrzeugreferenz);
}
