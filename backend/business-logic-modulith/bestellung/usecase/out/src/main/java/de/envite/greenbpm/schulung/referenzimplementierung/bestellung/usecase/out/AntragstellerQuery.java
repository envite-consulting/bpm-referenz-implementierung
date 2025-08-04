package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Antragstellerreferenz;

public interface AntragstellerQuery {

    boolean validateExistence(Antragstellerreferenz antragstellerreferenz);
}
